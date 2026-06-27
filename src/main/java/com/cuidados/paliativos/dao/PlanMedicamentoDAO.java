package com.cuidados.paliativos.dao;

public interface PlanMedicamentoDAO {
    void guardar(Long idPlan, Long idMedicamento);

    void eliminar(Long idPlan, Long idMedicamento);

    void eliminarPorPlan(Long idPlan);
}
