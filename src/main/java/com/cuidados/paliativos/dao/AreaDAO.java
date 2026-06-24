package com.cuidados.paliativos.dao;

import com.cuidados.paliativos.modelo.Area;

import java.util.List;

public interface AreaDAO {

    void guardar(Area area);

    void modificar(Area area);

    void eliminar(Long id);

    List<Area> listar();
}
