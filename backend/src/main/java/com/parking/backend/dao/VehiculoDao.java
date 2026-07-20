package com.parking.backend.dao;

import com.parking.backend.config.DatabaseConnection;
import com.parking.backend.model.Vehiculo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehiculoDao {
    public void insertar(Vehiculo vehiculo) throws SQLException {
        String sql = "INSERT INTO vehiculo (id_alumnos, id_tipo, otro_tipo) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, vehiculo.getIdAlumnos());
            statement.setInt(2, vehiculo.getIdTipo());
            statement.setString(3, vehiculo.getOtroTipo());
            statement.executeUpdate();
        }
    }

    public List<Vehiculo> listar() throws SQLException {
        List<Vehiculo> vehiculos = new ArrayList<>();
        String sql = "SELECT * FROM vehiculo";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Vehiculo vehiculo = new Vehiculo();
                vehiculo.setIdVehiculo(resultSet.getInt("id_vehiculo"));
                vehiculo.setIdAlumnos(resultSet.getInt("id_alumnos"));
                vehiculo.setIdTipo(resultSet.getInt("id_tipo"));
                vehiculo.setOtroTipo(resultSet.getString("otro_tipo"));
                vehiculos.add(vehiculo);
            }
        }
        return vehiculos;
    }
}
