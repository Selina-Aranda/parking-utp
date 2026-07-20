package com.parking.backend.service;

import com.parking.backend.model.Alumno;
import com.parking.backend.repository.AlumnoRepository;
import com.parking.backend.repository.JdbcAlumnoRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AlumnoService {
    private final AlumnoRepository alumnoRepository;

    public AlumnoService() {
        this.alumnoRepository = new JdbcAlumnoRepository();
    }

    public Alumno crearAlumno(Alumno alumno) throws SQLException {
        return alumnoRepository.save(alumno);
    }

    public Optional<Alumno> buscarPorId(Integer id) throws SQLException {
        return alumnoRepository.findById(id);
    }

    public Optional<Alumno> buscarPorCodigo(String codigo) throws SQLException {
        return alumnoRepository.findByCodigo(codigo);
    }

    public List<Alumno> listarAlumnos() throws SQLException {
        return alumnoRepository.findAll();
    }

    public void eliminarAlumno(Integer id) throws SQLException {
        alumnoRepository.deleteById(id);
    }
}
