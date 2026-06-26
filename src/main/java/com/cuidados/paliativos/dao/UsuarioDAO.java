package com.cuidados.paliativos.dao;

import com.cuidados.paliativos.modelo.Usuario;

import java.util.List;

public interface UsuarioDAO {
    Usuario buscarPorEmail(String email);

    List<Usuario> listar();
}
