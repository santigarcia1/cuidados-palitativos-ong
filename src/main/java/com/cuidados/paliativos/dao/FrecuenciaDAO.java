package com.cuidados.paliativos.dao;

import com.cuidados.paliativos.modelo.Frecuencia;

import java.util.List;

public interface FrecuenciaDAO {

    void guardar(Frecuencia frecuencia);

    void modificar(Frecuencia frecuencia);

    void eliminar(Long id);

    List<Frecuencia> listar();
}