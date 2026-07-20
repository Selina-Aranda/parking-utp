package com.parking.backend.model;

public class Vehiculo {
    private Integer idVehiculo;
    private Integer idAlumnos;
    private Integer idTipo;
    private String otroTipo;

    public Vehiculo() {
    }

    public Vehiculo(Integer idVehiculo, Integer idAlumnos, Integer idTipo, String otroTipo) {
        this.idVehiculo = idVehiculo;
        this.idAlumnos = idAlumnos;
        this.idTipo = idTipo;
        this.otroTipo = otroTipo;
    }

    public Integer getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(Integer idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public Integer getIdAlumnos() {
        return idAlumnos;
    }

    public void setIdAlumnos(Integer idAlumnos) {
        this.idAlumnos = idAlumnos;
    }

    public Integer getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(Integer idTipo) {
        this.idTipo = idTipo;
    }

    public String getOtroTipo() {
        return otroTipo;
    }

    public void setOtroTipo(String otroTipo) {
        this.otroTipo = otroTipo;
    }

    @Override
    public String toString() {
        return "Vehiculo{" +
                "idVehiculo=" + idVehiculo +
                ", idAlumnos=" + idAlumnos +
                ", idTipo=" + idTipo +
                ", otroTipo='" + otroTipo + '\'' +
                '}';
    }
}
