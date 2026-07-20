package com.parking.backend.dao;

import com.parking.backend.config.DatabaseConnection;
import com.parking.backend.model.Registro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RegistroDao {
    public void insertar(Registro registro) throws SQLException {
        String sql = "INSERT INTO registro (id_vehiculo, id_espacio, fecha_ingreso, fecha_salida, estado) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, registro.getIdVehiculo());
            statement.setInt(2, registro.getIdEspacio());
            statement.setTimestamp(3, Timestamp.valueOf(registro.getFechaIngreso()));
            statement.setTimestamp(4, registro.getFechaSalida() == null ? null : Timestamp.valueOf(registro.getFechaSalida()));
            statement.setString(5, registro.getEstado());
            statement.executeUpdate();
        }
    }

    public List<Registro> listarActivos() throws SQLException {
        List<Registro> registros = new ArrayList<>();
        String sql = "SELECT * FROM registro WHERE estado = 'Estacionado'";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Registro registro = new Registro();
                registro.setIdRegistro(resultSet.getInt("id_registro"));
                registro.setIdVehiculo(resultSet.getInt("id_vehiculo"));
                registro.setIdEspacio(resultSet.getInt("id_espacio"));
                registro.setFechaIngreso(resultSet.getTimestamp("fecha_ingreso").toLocalDateTime());
                Timestamp fechaSalida = resultSet.getTimestamp("fecha_salida");
                registro.setFechaSalida(fechaSalida == null ? null : fechaSalida.toLocalDateTime());
                registro.setEstado(resultSet.getString("estado"));
                registros.add(registro);
            }
        }
        return registros;
    }
}
