package com.cuidados.paliativos.modelo;

import java.util.Objects;

public class Especialidad {
    private Long id;
    private String nombre;
    private String descripcion;

    public Especialidad() {}
    public Especialidad(Long id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // Getters y setters
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

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;

        if (!(o instanceof Especialidad))
            return false;

        Especialidad esp = (Especialidad) o;

        return Objects.equals(id, esp.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

