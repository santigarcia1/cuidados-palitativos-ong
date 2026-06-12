package com.cuidados.paliativos.seguridad;

import com.cuidados.paliativos.modelo.Usuario;

public class Sesion {

    private static Sesion instancia;

    private Usuario usuarioLogueado;

    private Sesion() {
    }

    public static Sesion getInstancia() {
        if (instancia == null) {
            instancia = new Sesion();
        }
        return instancia;
    }

    public Usuario getUsuarioLogueado() {
        return usuarioLogueado;
    }

    public void setUsuarioLogueado(Usuario usuarioLogueado) {
        this.usuarioLogueado = usuarioLogueado;
    }

    public void cerrarSesion() {
        usuarioLogueado = null;
    }
}