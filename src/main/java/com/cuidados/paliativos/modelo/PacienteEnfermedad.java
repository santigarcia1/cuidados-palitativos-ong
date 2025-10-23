package com.cuidados.paliativos.modelo;

public class PacienteEnfermedad {
    private Paciente paciente;
    private Enfermedad enfermedad;

    public PacienteEnfermedad() {}

    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }
    public Enfermedad getEnfermedad() { return enfermedad; }
    public void setEnfermedad(Enfermedad enfermedad) { this.enfermedad = enfermedad; }
}
