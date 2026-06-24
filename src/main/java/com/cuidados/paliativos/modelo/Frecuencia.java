package com.cuidados.paliativos.modelo;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;

        if (!(o instanceof Frecuencia))
            return false;

        Frecuencia frecuencia = (Frecuencia) o;

        return Objects.equals(id, frecuencia.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
