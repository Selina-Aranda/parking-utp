package com.parking.backend.dao;

import com.parking.backend.config.DatabaseConnection;
import com.parking.backend.model.Alumno;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlumnoDao {
    public void insertar(Alumno alumno) throws SQLException {
        String sql = "INSERT INTO alumnos (codigo, nombres, apellidos, correo, carrera, estado) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, alumno.getCodigo());
            statement.setString(2, alumno.getNombres());
            statement.setString(3, alumno.getApellidos());
            statement.setString(4, alumno.getCorreo());
            statement.setString(5, alumno.getCarrera());
            statement.setString(6, alumno.getEstado());
            statement.executeUpdate();
        }
    }

    public List<Alumno> listar() throws SQLException {
        List<Alumno> alumnos = new ArrayList<>();
        String sql = "SELECT * FROM alumnos";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Alumno alumno = new Alumno();
                alumno.setIdAlumnos(resultSet.getInt("id_alumnos"));
                alumno.setCodigo(resultSet.getString("codigo"));
                alumno.setNombres(resultSet.getString("nombres"));
                alumno.setApellidos(resultSet.getString("apellidos"));
                alumno.setCorreo(resultSet.getString("correo"));
                alumno.setCarrera(resultSet.getString("carrera"));
                alumno.setEstado(resultSet.getString("estado"));
                alumnos.add(alumno);
            }
        }
        return alumnos;
    }
}
