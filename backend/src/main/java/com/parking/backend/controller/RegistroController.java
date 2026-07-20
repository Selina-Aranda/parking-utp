package com.parking.backend.controller;

import java.sql.SQLException;
import java.util.Map;

import com.parking.backend.service.RegistroService;

public class RegistroController {
    private final RegistroService registroService;

    public RegistroController() {
        this.registroService = new RegistroService();
    }

    public Map<String, Object> registrarVehiculo(Map<String, Object> payload) throws SQLException {
        String codigo = String.valueOf(payload.get("codigo"));
        Integer tipoVehiculo = Integer.valueOf(String.valueOf(payload.get("tipoVehiculo")));
        String otroTipo = payload.get("otroTipo") == null ? null : String.valueOf(payload.get("otroTipo"));

        return registroService.registrarVehiculo(codigo, tipoVehiculo, otroTipo);
    }

    public Map<String, Object> registrarSalida(Map<String, Object> payload) throws SQLException {
        String qrValue = payload.get("qrValue") == null ? null : String.valueOf(payload.get("qrValue"));
        return registroService.registrarSalida(qrValue);
    }

    public Map<String,Object> obtenerDashboard() throws SQLException {
        return registroService.obtenerDashboard();
    }
}
