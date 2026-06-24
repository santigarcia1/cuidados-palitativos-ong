package com.cuidados.paliativos.dao;

import com.cuidados.paliativos.modelo.Dieta;
import com.cuidados.paliativos.modelo.TipoDieta;
import com.cuidados.paliativos.persistencia.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DietaDAOImpl implements DietaDAO {

    @Override
    public void guardar(Dieta dieta) {

        String sql = """
                INSERT INTO dietas
                (nombre, descripcion, id_tipo_dieta)
                VALUES (?, ?, ?)
                """;

        try (
                Connection conn = ConexionBD.conectar();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setString(1, dieta.getNombre());
            ps.setString(2, dieta.getDescripcion());
            ps.setLong(
                    3,
                    dieta.getTipoDieta().getId()
            );

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void modificar(Dieta dieta) {

        String sql = """
                UPDATE dietas
                SET nombre = ?,
                    descripcion = ?,
                    id_tipo_dieta = ?
                WHERE id = ?
                """;

        try (
                Connection conn = ConexionBD.conectar();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setString(1, dieta.getNombre());
            ps.setString(2, dieta.getDescripcion());
            ps.setLong(
                    3,
                    dieta.getTipoDieta().getId()
            );
            ps.setLong(4, dieta.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(Long id) {

        String sql = """
                DELETE FROM dietas
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
    public List<Dieta> listar() {

        List<Dieta> dietas = new ArrayList<>();

        String sql = """
                SELECT d.*,
                       td.nombre AS nombre_tipo,
                       td.descripcion AS descripcion_tipo
                FROM dietas d
                INNER JOIN tipos_dieta td
                    ON d.id_tipo_dieta = td.id
                ORDER BY d.id
                """;

        try (
                Connection conn = ConexionBD.conectar();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                TipoDieta tipo = new TipoDieta();

                tipo.setId(
                        rs.getLong("id_tipo_dieta")
                );

                tipo.setNombre(
                        rs.getString("nombre_tipo")
                );

                tipo.setDescripcion(
                        rs.getString("descripcion_tipo")
                );

                Dieta dieta = new Dieta();

                dieta.setId(
                        rs.getLong("id")
                );

                dieta.setNombre(
                        rs.getString("nombre")
                );

                dieta.setDescripcion(
                        rs.getString("descripcion")
                );

                dieta.setTipoDieta(tipo);

                dietas.add(dieta);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dietas;
    }
}