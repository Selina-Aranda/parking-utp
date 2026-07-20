package com.parking.backend.controller;

import com.parking.backend.model.Alumno;
import com.parking.backend.service.AlumnoService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AlumnoController {
    private final AlumnoService alumnoService;

    public AlumnoController() {
        this.alumnoService = new AlumnoService();
    }

    public Alumno crear(Alumno alumno) throws SQLException {
        return alumnoService.crearAlumno(alumno);
    }

    public Optional<Alumno> obtenerPorId(Integer id) throws SQLException {
        return alumnoService.buscarPorId(id);
    }

    public List<Alumno> listar() throws SQLException {
        return alumnoService.listarAlumnos();
    }

    public void eliminar(Integer id) throws SQLException {
        alumnoService.eliminarAlumno(id);
    }
}
