package com.cuidados.paliativos.dao;

import com.cuidados.paliativos.modelo.Especialidad;
import com.cuidados.paliativos.modelo.Profesional;
import com.cuidados.paliativos.modelo.Usuario;
import com.cuidados.paliativos.persistencia.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfesionalDAOImpl implements ProfesionalDAO {

    @Override
    public void guardar(Profesional profesional) {
        String sql = """
                INSERT INTO profesionales
                (nombre, apellido, id_especialidad, telefono, id_usuario)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (
                Connection conn = ConexionBD.conectar();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setString(1, profesional.getNombre());
            ps.setString(2, profesional.getApellido());
            ps.setLong(3, profesional.getEspecialidad().getId());
            ps.setString(4, profesional.getTelefono());
            ps.setLong(5, profesional.getUsuario().getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void modificar(Profesional profesional) {

        String sql = """
                UPDATE profesionales
                SET nombre = ?,
                    apellido = ?,
                    id_especialidad = ?,
                    telefono = ?,
                    id_usuario = ?
                WHERE id = ?
                """;

        try (
                Connection conn = ConexionBD.conectar();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setString(1, profesional.getNombre());
            ps.setString(2, profesional.getApellido());
            ps.setLong(3, profesional.getEspecialidad().getId());
            ps.setString(4, profesional.getTelefono());
            ps.setLong(5, profesional.getUsuario().getId());
            ps.setLong(6, profesional.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(Long id) {

        String sql = """
                DELETE FROM profesionales
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
    public List<Profesional> listar() {
        List<Profesional> profesionales = new ArrayList<>();

        String sql = """
                SELECT p.*, e.id as id_especialidad, e.nombre as nombre_especialidad,
                u.id as id_usuario, u.email as email_usuario
                FROM profesionales p
                JOIN especialidades e ON p.id_especialidad = e.id
                JOIN usuarios u ON p.id_usuario = u.id
                ORDER BY p.apellido, p.nombre
                """;

        try (
                Connection conn = ConexionBD.conectar();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Profesional profesional = new Profesional();

                profesional.setId(rs.getLong("id"));
                profesional.setNombre(rs.getString("nombre"));
                profesional.setApellido(rs.getString("apellido"));
                profesional.setTelefono(rs.getString("telefono"));

                Especialidad especialidad = new Especialidad();
                especialidad.setId(rs.getLong("id_especialidad"));
                especialidad.setNombre(rs.getString("nombre_especialidad"));
                profesional.setEspecialidad(especialidad);

                Usuario usuario = new Usuario();
                usuario.setId(rs.getLong("id_usuario"));
                usuario.setEmail(rs.getString("email_usuario"));
                profesional.setUsuario(usuario);

                profesionales.add(profesional);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return profesionales;
    }
}