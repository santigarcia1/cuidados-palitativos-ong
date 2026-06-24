package com.cuidados.paliativos.dao;

import com.cuidados.paliativos.modelo.EstadoUsuario;

import java.util.List;

public interface EstadoUsuarioDAO {
    List<EstadoUsuario> listarTodos();
    EstadoUsuario buscarPorId(Long id);
}
