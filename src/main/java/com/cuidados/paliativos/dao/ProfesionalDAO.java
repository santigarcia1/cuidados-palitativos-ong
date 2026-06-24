package com.cuidados.paliativos.dao;

import com.cuidados.paliativos.modelo.Profesional;

import java.util.List;

public interface ProfesionalDAO {

    void guardar(Profesional profesional);

    void modificar(Profesional profesional);

    void eliminar(Long id);

    List<Profesional> listar();
}
