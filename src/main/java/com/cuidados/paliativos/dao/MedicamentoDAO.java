package com.cuidados.paliativos.dao;

import com.cuidados.paliativos.modelo.Medicamento;

import java.util.ArrayList;
import java.util.List;

public interface MedicamentoDAO {

    Long guardar(Medicamento medicamento);

    void modificar(Medicamento medicamento);

    void eliminar(Long id);

    List<Medicamento> listar();

    List<Medicamento> buscarPorIdDePlanCuidado(Long idPlanCuidado);
}