package com.cuidados.paliativos.dao;

import com.cuidados.paliativos.modelo.PlanCuidado;

import java.util.List;

public interface PlanCuidadoDAO {
    void guardar(PlanCuidado plan);

    void modificar(PlanCuidado plan);

    void eliminar(Long id);

    List<PlanCuidado> listar();
}
