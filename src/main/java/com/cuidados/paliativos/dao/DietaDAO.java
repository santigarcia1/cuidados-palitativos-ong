package com.cuidados.paliativos.dao;

import com.cuidados.paliativos.modelo.Dieta;

import java.util.List;

public interface DietaDAO {

    Long guardar(Dieta dieta);

    void modificar(Dieta dieta);

    void eliminar(Long id);

    List<Dieta> listar();

    List<Dieta> buscarPorIdDePlanDeCuidado(Long idPlanCuidado);
}