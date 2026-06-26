package com.cuidados.paliativos.dao;

import com.cuidados.paliativos.modelo.DetalleDieta;

import java.util.List;

public interface DetalleDietaDAO {

    void guardar(DetalleDieta detalle);

    void modificar(DetalleDieta detalle);

    void eliminar(Long id);

    List<DetalleDieta> listarPorDieta(Long idDieta);
}