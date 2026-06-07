package com.cuidados.paliativos.seguridad;

import com.cuidados.paliativos.modelo.Usuario;

public class Sesion {

    private static Usuario usuarioLogueado;

    private Sesion() {
    }

    public static Usuario getUsuarioLogueado() {
        return usuarioLogueado;
    }

    public static void setUsuarioLogueado(Usuario usuario) {
        usuarioLogueado = usuario;
    }

    public static void cerrarSesion() {
        usuarioLogueado = null;
    }
}