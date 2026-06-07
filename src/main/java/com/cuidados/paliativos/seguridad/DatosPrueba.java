package com.cuidados.paliativos.seguridad;

import com.cuidados.paliativos.modelo.Rol;
import com.cuidados.paliativos.modelo.Usuario;

import java.util.ArrayList;
import java.util.List;

public class DatosPrueba {

    public static List<Usuario> getUsuarios() {

        List<Usuario> usuarios = new ArrayList<>();

        Rol administrador = new Rol(
                1L,
                "ADMINISTRADOR",
                "Acceso total"
        );

        Rol administrativo = new Rol(
                2L,
                "ADMINISTRATIVO",
                "Gestión operativa"
        );

        Rol profesional = new Rol(
                3L,
                "PROFESIONAL",
                "Atención médica"
        );

        Rol voluntario = new Rol(
                4L,
                "VOLUNTARIO",
                "Consultas y asistencia"
        );

        Usuario u1 = new Usuario();
        u1.setEmail("admin@ong.com");
        u1.setContrasena(
                SeguridadUtil.encriptar("admin123"));
        u1.setRol(administrador);

        Usuario u2 = new Usuario();
        u2.setEmail("administrativo@ong.com");
        u2.setContrasena(
                SeguridadUtil.encriptar("admin456"));
        u2.setRol(administrativo);

        Usuario u3 = new Usuario();
        u3.setEmail("profesional@ong.com");
        u3.setContrasena(
                SeguridadUtil.encriptar("prof123"));
        u3.setRol(profesional);

        Usuario u4 = new Usuario();
        u4.setEmail("voluntario@ong.com");
        u4.setContrasena(
                SeguridadUtil.encriptar("vol123"));
        u4.setRol(voluntario);

        usuarios.add(u1);
        usuarios.add(u2);
        usuarios.add(u3);
        usuarios.add(u4);

        return usuarios;
    }
}