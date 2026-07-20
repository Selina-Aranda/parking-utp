package com.parking.backend.repository;

import com.parking.backend.model.Alumno;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface AlumnoRepository {
    Alumno save(Alumno alumno) throws SQLException;

    Optional<Alumno> findById(Integer id) throws SQLException;

    Optional<Alumno> findByCodigo(String codigo) throws SQLException;

    List<Alumno> findAll() throws SQLException;

    void deleteById(Integer id) throws SQLException;
}
