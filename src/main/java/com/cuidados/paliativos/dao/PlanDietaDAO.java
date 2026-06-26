package com.cuidados.paliativos.dao;

import com.cuidados.paliativos.modelo.PlanDieta;

public interface PlanDietaDAO {
    void guardar(Long idPlan, Long idDieta);

    void eliminar(Long idPlan, Long idDieta);
}
