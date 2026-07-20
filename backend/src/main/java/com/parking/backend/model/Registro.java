package com.parking.backend.model;

import java.time.LocalDateTime;

public class Registro {
    private Integer idRegistro;
    private Integer idVehiculo;
    private Integer idEspacio;
    private LocalDateTime fechaIngreso;
    private LocalDateTime fechaSalida;
    private String estado;

    public Registro() {
    }

    public Registro(Integer idRegistro, Integer idVehiculo, Integer idEspacio, LocalDateTime fechaIngreso, LocalDateTime fechaSalida, String estado) {
        this.idRegistro = idRegistro;
        this.idVehiculo = idVehiculo;
        this.idEspacio = idEspacio;
        this.fechaIngreso = fechaIngreso;
        this.fechaSalida = fechaSalida;
        this.estado = estado;
    }

    public Integer getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(Integer idRegistro) {
        this.idRegistro = idRegistro;
    }

    public Integer getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(Integer idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public Integer getIdEspacio() {
        return idEspacio;
    }

    public void setIdEspacio(Integer idEspacio) {
        this.idEspacio = idEspacio;
    }

    public LocalDateTime getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDateTime fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public LocalDateTime getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDateTime fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Registro{" +
                "idRegistro=" + idRegistro +
                ", idVehiculo=" + idVehiculo +
                ", idEspacio=" + idEspacio +
                ", fechaIngreso=" + fechaIngreso +
                ", fechaSalida=" + fechaSalida +
                ", estado='" + estado + '\'' +
                '}';
    }
}
