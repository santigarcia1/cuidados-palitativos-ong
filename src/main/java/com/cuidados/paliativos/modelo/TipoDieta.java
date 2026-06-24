package com.cuidados.paliativos.modelo;

import java.util.Objects;

public class TipoDieta {
    private Long id;
    private String nombre;
    private String descripcion;

    public TipoDieta() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;

        if (!(o instanceof TipoDieta))
            return false;

        TipoDieta tipoDieta = (TipoDieta) o;

        return Objects.equals(id, tipoDieta.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}