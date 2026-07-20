package com.parking.backend.repository;

import com.parking.backend.config.DatabaseConnection;
import com.parking.backend.model.Alumno;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcAlumnoRepository implements AlumnoRepository {

    @Override
    public Alumno save(Alumno alumno) throws SQLException {
        String sql = "INSERT INTO alumnos (codigo, nombres, apellidos, correo, carrera, estado) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, alumno.getCodigo());
            statement.setString(2, alumno.getNombres());
            statement.setString(3, alumno.getApellidos());
            statement.setString(4, alumno.getCorreo());
            statement.setString(5, alumno.getCarrera());
            statement.setString(6, alumno.getEstado());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    alumno.setIdAlumnos(generatedKeys.getInt(1));
                }
            }
            return alumno;
        }
    }

    @Override
    public Optional<Alumno> findById(Integer id) throws SQLException {
        String sql = "SELECT * FROM alumnos WHERE id_alumnos = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapRow(resultSet));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Alumno> findByCodigo(String codigo) throws SQLException {
        String sql = "SELECT * FROM alumnos WHERE codigo = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, codigo);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapRow(resultSet));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Alumno> findAll() throws SQLException {
        List<Alumno> alumnos = new ArrayList<>();
        String sql = "SELECT * FROM alumnos";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                alumnos.add(mapRow(resultSet));
            }
        }
        return alumnos;
    }

    @Override
    public void deleteById(Integer id) throws SQLException {
        String sql = "DELETE FROM alumnos WHERE id_alumnos = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    private Alumno mapRow(ResultSet resultSet) throws SQLException {
        Alumno alumno = new Alumno();
        alumno.setIdAlumnos(resultSet.getInt("id_alumnos"));
        alumno.setCodigo(resultSet.getString("codigo"));
        alumno.setNombres(resultSet.getString("nombres"));
        alumno.setApellidos(resultSet.getString("apellidos"));
        alumno.setCorreo(resultSet.getString("correo"));
        alumno.setCarrera(resultSet.getString("carrera"));
        alumno.setEstado(resultSet.getString("estado"));
        return alumno;
    }
}
