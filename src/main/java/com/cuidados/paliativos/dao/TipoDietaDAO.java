package com.cuidados.paliativos.dao;

import com.cuidados.paliativos.modelo.TipoDieta;

import java.util.List;

public interface TipoDietaDAO {

    void guardar(TipoDieta tipoDieta);

    void modificar(TipoDieta tipoDieta);

    void eliminar(Long id);

    List<TipoDieta> listar();
}