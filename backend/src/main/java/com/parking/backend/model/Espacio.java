package com.parking.backend.model;

public class Espacio {
    private Integer idEspacio;
    private String codigo;
    private Integer idParqueadero;
    private String estado;

    public Espacio() {
    }

    public Espacio(Integer idEspacio, String codigo, Integer idParqueadero, String estado) {
        this.idEspacio = idEspacio;
        this.codigo = codigo;
        this.idParqueadero = idParqueadero;
        this.estado = estado;
    }

    public Integer getIdEspacio() {
        return idEspacio;
    }

    public void setIdEspacio(Integer idEspacio) {
        this.idEspacio = idEspacio;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getIdParqueadero() {
        return idParqueadero;
    }

    public void setIdParqueadero(Integer idParqueadero) {
        this.idParqueadero = idParqueadero;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Espacio{" +
                "idEspacio=" + idEspacio +
                ", codigo='" + codigo + '\'' +
                ", idParqueadero=" + idParqueadero +
                ", estado='" + estado + '\'' +
                '}';
    }
}
