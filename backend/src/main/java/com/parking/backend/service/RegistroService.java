package com.parking.backend.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.parking.backend.config.DatabaseConnection;
import com.parking.backend.model.Alumno;
import com.parking.backend.repository.JdbcAlumnoRepository;

public class RegistroService {
    private final JdbcAlumnoRepository alumnoRepository = new JdbcAlumnoRepository();

    public Map<String, Object> registrarVehiculo(String codigo, Integer tipoVehiculo, String otroTipo)
            throws SQLException {
        String normalizedCode = codigo == null ? "" : codigo.trim().toUpperCase();
        if (!normalizedCode.startsWith("U") || normalizedCode.length() < 8) {
            throw new SQLException("El código debe iniciar con U y tener un formato válido de UTP.");
        }

        Alumno alumno = alumnoRepository.findByCodigo(normalizedCode)
                .orElseThrow(() -> new SQLException("El código estudiantil no es válido."));

        if (!"Activo".equalsIgnoreCase(alumno.getEstado())) {
            throw new SQLException("El alumno no se encuentra activo.");
        }

        if (tieneIngresoActivo(alumno.getIdAlumnos())) {
            throw new SQLException(
                    "El estudiante ya tiene un vehículo registrado dentro del estacionamiento. Debe registrar su salida antes de volver a ingresar.");
        }

        String qrValue = "UTP-" + normalizedCode + "-" + System.currentTimeMillis();

        try (Connection connection = DatabaseConnection.getConnection()) {
            Integer vehiculoId = obtenerOCrearVehiculo(connection, alumno.getIdAlumnos(), tipoVehiculo, otroTipo);

            String espacioSql = """
                SELECT id_espacio, codigo
                FROM espacio
                WHERE estado = 'Disponible'
                ORDER BY RAND()
                LIMIT 1
                """;
            try (PreparedStatement statement = connection.prepareStatement(espacioSql);
                    ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    throw new SQLException("No hay espacios disponibles en este momento.");
                }

                Integer espacioId = resultSet.getInt("id_espacio");
                String espacioCodigo = resultSet.getString("codigo");

                String updateEspacio = "UPDATE espacio SET estado = 'Ocupado' WHERE id_espacio = ?";
                try (PreparedStatement update = connection.prepareStatement(updateEspacio)) {
                    update.setInt(1, espacioId);
                    update.executeUpdate();
                }

                String insertRegistro = "INSERT INTO registro (id_vehiculo, id_espacio, fecha_ingreso, fecha_salida, estado) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement insert = connection.prepareStatement(insertRegistro)) {
                    insert.setInt(1, vehiculoId);
                    insert.setInt(2, espacioId);
                    insert.setTimestamp(3, java.sql.Timestamp.valueOf(LocalDateTime.now()));
                    insert.setNull(4, java.sql.Types.TIMESTAMP);
                    insert.setString(5, "Estacionado");
                    insert.executeUpdate();
                }

                EmailService emailService = new EmailService();
                boolean emailSent = emailService.sendQrEmail(alumno.getCorreo(), qrValue, espacioCodigo);

                Map<String, Object> response = new HashMap<>();
                response.put("message", "Registro creado correctamente.");
                response.put("qrValue", qrValue);
                response.put("spaceCode", espacioCodigo);
                response.put("alumno", alumno.getCodigo());
                response.put("emailSent", emailSent);
                return response;
            }
        }
    }

    public Map<String, Object> registrarSalida(String qrValue) throws SQLException {
        if (qrValue == null || qrValue.isBlank()) {
            throw new SQLException("No se recibió un código QR válido.");
        }

        String normalizedQr = qrValue.trim();
        System.out.println("[SALIDA] QR escaneado: " + normalizedQr);

        String codigo = extraerCodigoEstudiante(normalizedQr);
        if (codigo == null) {
            throw new SQLException("El QR no corresponde a un estudiante registrado.");
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            String selectSql = """
                    SELECT a.codigo, a.nombres, a.apellidos, a.correo, a.carrera,
                           v.id_vehiculo, tv.nombre AS tipo_vehiculo, v.otro_tipo,
                           e.id_espacio, e.codigo AS espacio_codigo, r.id_registro, r.fecha_ingreso
                    FROM registro r
                    JOIN vehiculo v ON r.id_vehiculo = v.id_vehiculo
                    JOIN alumnos a ON v.id_alumnos = a.id_alumnos
                    LEFT JOIN tipo_vehiculo tv ON v.id_tipo = tv.id_tipo
                    JOIN espacio e ON r.id_espacio = e.id_espacio
                    WHERE a.codigo = ? AND r.estado = 'Estacionado'
                    ORDER BY r.id_registro DESC
                    LIMIT 1
                    """;

            try (PreparedStatement statement = connection.prepareStatement(selectSql)) {
                statement.setString(1, codigo);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (!resultSet.next()) {
                        throw new SQLException("No existe un ingreso activo para ese QR.");
                    }

                    Integer registroId = resultSet.getInt("id_registro");
                    Integer espacioId = resultSet.getInt("id_espacio");
                    String espacioCodigo = resultSet.getString("espacio_codigo");
                    String nombres = resultSet.getString("nombres");
                    String apellidos = resultSet.getString("apellidos");
                    String carrera = resultSet.getString("carrera");
                    String tipoVehiculo = resultSet.getString("tipo_vehiculo");
                    String otroTipo = resultSet.getString("otro_tipo");
                    LocalDateTime fechaIngreso = resultSet.getTimestamp("fecha_ingreso").toLocalDateTime();

                    String updateRegistroSql = "UPDATE registro SET estado = 'Finalizado', fecha_salida = ? WHERE id_registro = ?";
                    try (PreparedStatement updateRegistro = connection.prepareStatement(updateRegistroSql)) {
                        updateRegistro.setTimestamp(1, java.sql.Timestamp.valueOf(LocalDateTime.now()));
                        updateRegistro.setInt(2, registroId);
                        updateRegistro.executeUpdate();
                    }

                    String updateEspacioSql = "UPDATE espacio SET estado = 'Disponible' WHERE id_espacio = ?";
                    try (PreparedStatement updateEspacio = connection.prepareStatement(updateEspacioSql)) {
                        updateEspacio.setInt(1, espacioId);
                        updateEspacio.executeUpdate();
                    }

                    Map<String, Object> response = new HashMap<>();
                    response.put("message", "Salida registrada correctamente.");
                    response.put("qrValue", normalizedQr);
                    response.put("studentCode", codigo);
                    response.put("studentName", nombres + " " + apellidos);
                    response.put("career", carrera);
                    response.put("vehicleType", tipoVehiculo != null && !tipoVehiculo.isBlank() ? tipoVehiculo
                            : (otroTipo != null ? otroTipo : "No registrado"));
                    response.put("spaceCode", espacioCodigo);
                    response.put("entryTime", fechaIngreso.toString());
                    response.put("exitTime", LocalDateTime.now().toString());
                    return response;
                }
            }
        }
    }

    private String extraerCodigoEstudiante(String qrValue) {
        Pattern pattern = Pattern.compile("U\\d{7,8}");
        Matcher matcher = pattern.matcher(qrValue);
        return matcher.find() ? matcher.group() : null;
    }

    private Integer obtenerOCrearVehiculo(Connection connection, Integer idAlumno, Integer tipoVehiculo,
            String otroTipo) throws SQLException {
        String selectSql = "SELECT id_vehiculo FROM vehiculo WHERE id_alumnos = ?";
        try (PreparedStatement select = connection.prepareStatement(selectSql)) {
            select.setInt(1, idAlumno);
            try (ResultSet resultSet = select.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id_vehiculo");
                }
            }
        }

        String insertSql = "INSERT INTO vehiculo (id_alumnos, id_tipo, otro_tipo) VALUES (?, ?, ?)";
        try (PreparedStatement insert = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
            insert.setInt(1, idAlumno);
            insert.setInt(2, tipoVehiculo);
            insert.setString(3, otroTipo);
            insert.executeUpdate();

            try (ResultSet generatedKeys = insert.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        }

        throw new SQLException("No se pudo registrar el vehículo del alumno.");
    }

    private boolean tieneIngresoActivo(Integer idAlumno) throws SQLException {

        String sql = """
                SELECT 1
                FROM registro r
                INNER JOIN vehiculo v
                    ON r.id_vehiculo = v.id_vehiculo
                WHERE v.id_alumnos = ?
                AND r.estado = 'Estacionado'
                LIMIT 1
                """;

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, idAlumno);

            try (ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }
        }
    }

    //Datos dashboard
    public Map<String, Object> obtenerDashboard() throws SQLException {

    Map<String, Object> dashboard = new HashMap<>();

    try(Connection connection = DatabaseConnection.getConnection()){

        String sql = """
            SELECT
                COUNT(*) AS total,
                SUM(CASE WHEN estado='Disponible' THEN 1 ELSE 0 END) disponibles,
                SUM(CASE WHEN estado='Ocupado' THEN 1 ELSE 0 END) ocupados
            FROM espacio
        """;

        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        if(rs.next()){

            int total = rs.getInt("total");
            int disponibles = rs.getInt("disponibles");
            int ocupados = rs.getInt("ocupados");

            int porcentaje = total == 0
                    ? 0
                    : (ocupados * 100) / total;

            dashboard.put("available", disponibles);
            dashboard.put("occupied", ocupados);
            dashboard.put("total", total);
            dashboard.put("percentage", porcentaje);

        }

    }

    return dashboard;
}
}
