package com.cuidados.paliativos.modelo;

public class DetalleDieta {
    public enum Horario {
        DESAYUNO, ALMUERZO, MERIENDA, CENA
    }
    
    private Long id;
    private Dieta dieta;
    private Horario horario;
    private String descripcion;

    public DetalleDieta() {}

    public DetalleDieta(Long id, Dieta dieta, Horario horario, String descripcion) {
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

    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
