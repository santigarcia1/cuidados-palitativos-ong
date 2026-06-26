package com.cuidados.paliativos.modelo;

import java.util.Objects;

public class Medicamento {
    private Long id;
    private String nombre;
    private String dosis;
    private Frecuencia frecuencia;

    public Medicamento() {}

    public Medicamento(String nombre, String dosis, Frecuencia frecuencia) {
        this.nombre = nombre;
        this.dosis = dosis;
        this.frecuencia = frecuencia;
    }

    // Getters and setters
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

    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public Frecuencia getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(Frecuencia frecuencia) {
        this.frecuencia = frecuencia;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;

        if (!(o instanceof Medicamento))
            return false;

        Medicamento medicamento = (Medicamento) o;

        return Objects.equals(id, medicamento.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
