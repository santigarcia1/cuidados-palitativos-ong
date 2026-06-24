package com.cuidados.paliativos.dao;

import com.cuidados.paliativos.modelo.Paciente;

import java.util.List;

public interface PacienteDAO {

    void guardar(Paciente paciente);

    void modificar(Paciente paciente);

    void eliminar(Long id);

    List<Paciente> listar();
}
