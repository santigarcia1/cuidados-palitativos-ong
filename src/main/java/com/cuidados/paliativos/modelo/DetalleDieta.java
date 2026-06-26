package com.cuidados.paliativos.modelo;

public class DetalleDieta {
    private Long id;
    private Dieta dieta;
    private String horario;
    private String descripcion;

    public DetalleDieta() {}

    public DetalleDieta(Long id, Dieta dieta, String horario, String descripcion) {
        this.id = id;
        this.dieta = dieta;
        this.horario = horario;
        this.descripcion = descripcion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Dieta getDieta() {
        return dieta;
    }

    public void setDieta(Dieta dieta) {
        this.dieta = dieta;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
