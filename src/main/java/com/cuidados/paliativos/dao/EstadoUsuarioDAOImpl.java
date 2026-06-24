package com.cuidados.paliativos.dao;

import com.cuidados.paliativos.modelo.EstadoUsuario;
import com.cuidados.paliativos.persistencia.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EstadoUsuarioDAOImpl implements EstadoUsuarioDAO {

    public List<EstadoUsuario> listarTodos() {

        List<EstadoUsuario> estados = new ArrayList<>();

        String sql =
                "SELECT id, nombre, descripcion " +
                        "FROM estado_usuario " +
                        "ORDER BY id";

        try (
                Connection conn = ConexionBD.conectar();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                EstadoUsuario estado = new EstadoUsuario();

                estado.setId(rs.getLong("id"));
                estado.setNombre(rs.getString("nombre"));
                estado.setDescripcion(rs.getString("descripcion"));

                estados.add(estado);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return estados;
    }

    public EstadoUsuario buscarPorId(Long id) {

        String sql =
                "SELECT id, nombre, descripcion " +
                        "FROM estado_usuario " +
                        "WHERE id = ?";

        try (
                Connection conn = ConexionBD.conectar();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    EstadoUsuario estado = new EstadoUsuario();

                    estado.setId(rs.getLong("id"));
                    estado.setNombre(rs.getString("nombre"));
                    estado.setDescripcion(rs.getString("descripcion"));

                    return estado;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
