package com.cuidados.paliativos.dao;

import com.cuidados.paliativos.modelo.Voluntario;

import java.util.List;

public interface VoluntarioDAO {

    void guardar(Voluntario voluntario);

    void modificar(Voluntario voluntario);

    void eliminar(Long id);

    List<Voluntario> listar();
}