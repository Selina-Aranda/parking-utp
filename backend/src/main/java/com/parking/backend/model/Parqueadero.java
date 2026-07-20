package com.parking.backend.model;

public class Parqueadero {
    private Integer idParqueadero;
    private String ubicacion;
    private Integer capacidad;

    public Parqueadero() {
    }

    public Parqueadero(Integer idParqueadero, String ubicacion, Integer capacidad) {
        this.idParqueadero = idParqueadero;
        this.ubicacion = ubicacion;
        this.capacidad = capacidad;
    }

    public Integer getIdParqueadero() {
        return idParqueadero;
    }

    public void setIdParqueadero(Integer idParqueadero) {
        this.idParqueadero = idParqueadero;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    @Override
    public String toString() {
        return "Parqueadero{" +
                "idParqueadero=" + idParqueadero +
                ", ubicacion='" + ubicacion + '\'' +
                ", capacidad=" + capacidad +
                '}';
    }
}
