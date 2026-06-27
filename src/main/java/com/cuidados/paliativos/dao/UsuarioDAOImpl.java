package com.cuidados.paliativos.dao;

import com.cuidados.paliativos.modelo.*;
import com.cuidados.paliativos.persistencia.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAOImpl implements UsuarioDAO {
    @Override
    public Usuario buscarPorEmail(String email) {

        String sql = """
                SELECT u.*, e.id as id_estado, e.nombre as nombre_estado,
                r.id as id_rol, r.nombre as nombre_rol
                FROM usuarios u 
                JOIN roles r ON u.id_rol = r.id
                JOIN estado_usuario e ON u.id_estado = e.id
                WHERE u.email = ?
                AND u.id_estado = 1
                """;

        try (
                Connection conn = ConexionBD.conectar();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            }

            rs.next();

            Usuario usuario = new Usuario();
            usuario.setId(rs.getLong("id"));
            usuario.setEmail(rs.getString("email"));
            usuario.setContrasena(rs.getString("contraseña"));

            EstadoUsuario estado = new EstadoUsuario();
            estado.setId(rs.getLong("id_estado"));
            estado.setNombre(rs.getString("nombre_estado"));
            usuario.setEstado(estado);

            Rol rol = new Rol();
            rol.setId(rs.getLong("id_rol"));
            rol.setNombre(rs.getString("nombre_rol"));
            usuario.setRol(rol);

            return usuario;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Usuario> listar() {
        List<Usuario> usuarios = new ArrayList<>();

        String sql = """
                SELECT u.*, e.id as id_estado, e.nombre as nombre_estado,
                r.id as id_rol, r.nombre as nombre_rol
                FROM usuarios u
                JOIN roles r ON u.id_rol = r.id
                JOIN estado_usuario e ON u.id_estado = e.id
                ORDER BY u.email
                """;

        try (
                Connection conn = ConexionBD.conectar();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Usuario usuario = new Usuario();

                usuario.setId(rs.getLong("id"));
                usuario.setEmail(rs.getString("email"));
                usuario.setContrasena(rs.getString("contraseña"));

                EstadoUsuario estado = new EstadoUsuario();
                estado.setId(rs.getLong("id_estado"));
                estado.setNombre(rs.getString("nombre_estado"));
                usuario.setEstado(estado);

                Rol rol = new Rol();
                rol.setId(rs.getLong("id_rol"));
                rol.setNombre(rs.getString("nombre_rol"));
                usuario.setRol(rol);

                usuarios.add(usuario);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuarios;
    }
}
