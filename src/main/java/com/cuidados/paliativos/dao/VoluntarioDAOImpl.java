package com.cuidados.paliativos.dao;

import com.cuidados.paliativos.modelo.Area;
import com.cuidados.paliativos.modelo.Usuario;
import com.cuidados.paliativos.modelo.Voluntario;
import com.cuidados.paliativos.persistencia.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VoluntarioDAOImpl implements VoluntarioDAO {

    @Override
    public void guardar(Voluntario voluntario) {

        String sql = """
                INSERT INTO voluntarios
                (nombre, apellido, telefono, id_area, id_usuario)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (
                Connection conn = ConexionBD.conectar();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setString(1, voluntario.getNombre());
            ps.setString(2, voluntario.getApellido());
            ps.setString(3, voluntario.getTelefono());
            ps.setLong(4, voluntario.getArea().getId());
            ps.setLong(5, voluntario.getUsuario().getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void modificar(Voluntario voluntario) {

        String sql = """
                UPDATE voluntarios
                SET nombre = ?,
                    apellido = ?,
                    telefono = ?,
                    id_area = ?,
                    id_usuario = ?
                WHERE id = ?
                """;

        try (
                Connection conn = ConexionBD.conectar();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setString(1, voluntario.getNombre());
            ps.setString(2, voluntario.getApellido());
            ps.setString(3, voluntario.getTelefono());
            ps.setLong(4, voluntario.getArea().getId());
            ps.setLong(5, voluntario.getUsuario().getId());
            ps.setLong(6, voluntario.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(Long id) {

        String sql = """
                DELETE FROM voluntarios
                WHERE id = ?
                """;

        try (
                Connection conn = ConexionBD.conectar();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setLong(1, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Voluntario> listar() {

        List<Voluntario> voluntarios = new ArrayList<>();

        String sql = """
                SELECT v.*,
                       a.nombre AS nombre_area,
                       u.email AS email_usuario
                FROM voluntarios v
                INNER JOIN areas a
                    ON v.id_area = a.id
                INNER JOIN usuarios u
                    ON v.id_usuario = u.id
                ORDER BY v.id
                """;

        try (
                Connection conn = ConexionBD.conectar();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Area area = new Area();
                area.setId(rs.getLong("id_area"));
                area.setNombre(rs.getString("nombre_area"));

                Usuario usuario = new Usuario();
                usuario.setId(rs.getLong("id_usuario"));
                usuario.setEmail(rs.getString("email_usuario"));

                Voluntario voluntario = new Voluntario();

                voluntario.setId(rs.getLong("id"));
                voluntario.setNombre(rs.getString("nombre"));
                voluntario.setApellido(rs.getString("apellido"));
                voluntario.setTelefono(rs.getString("telefono"));
                voluntario.setArea(area);
                voluntario.setUsuario(usuario);

                voluntarios.add(voluntario);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return voluntarios;
    }
}