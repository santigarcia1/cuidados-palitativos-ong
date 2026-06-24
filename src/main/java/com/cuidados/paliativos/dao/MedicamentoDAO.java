package com.cuidados.paliativos.dao;

import com.cuidados.paliativos.modelo.Medicamento;

import java.util.List;

public interface MedicamentoDAO {

    void guardar(Medicamento medicamento);

    void modificar(Medicamento medicamento);

    void eliminar(Long id);

    List<Medicamento> listar();
}