package com.cuidados.paliativos.modelo;

import java.util.List;

public class Dieta {
    private Long id;
    private String nombre;
    private String descripcion;
    private TipoDieta tipoDieta;
    private List<DetalleDieta> detalles;

    public Dieta() {}

    public Dieta(Long id, String nombre, String descripcion, TipoDieta tipoDieta) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipoDieta = tipoDieta;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TipoDieta getTipoDieta() {
        return tipoDieta;
    }

    public void setTipoDieta(TipoDieta tipoDieta) {
        this.tipoDieta = tipoDieta;
    }

    public List<DetalleDieta> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleDieta> detalles) {
        this.detalles = detalles;
    }
}
