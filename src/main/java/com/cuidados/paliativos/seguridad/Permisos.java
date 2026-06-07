package com.cuidados.paliativos.seguridad;

import com.cuidados.paliativos.modelo.Usuario;

public class Permisos {

    private Permisos() {
    }

    public static boolean esAdministrador(Usuario usuario) {

        return usuario != null
                && usuario.getRol() != null
                && "ADMINISTRADOR".equalsIgnoreCase(
                usuario.getRol().getNombre());
    }

    public static boolean esAdministrativo(Usuario usuario) {

        return usuario != null
                && usuario.getRol() != null
                && "ADMINISTRATIVO".equalsIgnoreCase(
                usuario.getRol().getNombre());
    }

    public static boolean esProfesional(Usuario usuario) {

        return usuario != null
                && usuario.getRol() != null
                && "PROFESIONAL".equalsIgnoreCase(
                usuario.getRol().getNombre());
    }

    public static boolean esVoluntario(Usuario usuario) {

        return usuario != null
                && usuario.getRol() != null
                && "VOLUNTARIO".equalsIgnoreCase(
                usuario.getRol().getNombre());
    }
}