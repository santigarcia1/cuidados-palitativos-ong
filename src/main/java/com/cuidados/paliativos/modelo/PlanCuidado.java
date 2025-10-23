package com.cuidados.paliativos.modelo;

import java.time.LocalDate;
import java.util.List;

public class PlanCuidado {
    private Long id;
    private LocalDate fechaCreacion;
    private String observaciones;
    private Paciente paciente;
    private Profesional profesional;
    private List<PlanMedicamento> medicamentos;
    private List<PlanDieta> dietas;

    public PlanCuidado() {}

    public PlanCuidado(Long id, LocalDate fechaCreacion, String observaciones, 
                      Paciente paciente, Profesional profesional) {
        this.id = id;
        this.fechaCreacion = fechaCreacion;
        this.observaciones = observaciones;
        this.paciente = paciente;
        this.profesional = profesional;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Profesional getProfesional() {
        return profesional;
    }

    public void setProfesional(Profesional profesional) {
        this.profesional = profesional;
    }

    public List<PlanMedicamento> getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(List<PlanMedicamento> medicamentos) {
        this.medicamentos = medicamentos;
    }

    public List<PlanDieta> getDietas() {
        return dietas;
    }

    public void setDietas(List<PlanDieta> dietas) {
        this.dietas = dietas;
    }
}
