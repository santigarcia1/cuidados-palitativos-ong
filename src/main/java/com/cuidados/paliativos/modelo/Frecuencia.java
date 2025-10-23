package com.cuidados.paliativos.modelo;

public class Frecuencia {
    private Long id;
    private String descripcion;

    public Frecuencia() {}

    public Frecuencia(Long id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
