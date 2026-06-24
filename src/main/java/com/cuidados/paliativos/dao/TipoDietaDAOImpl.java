package com.cuidados.paliativos.dao;

import com.cuidados.paliativos.modelo.TipoDieta;
import com.cuidados.paliativos.persistencia.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TipoDietaDAOImpl implements TipoDietaDAO {

    @Override
    public void guardar(TipoDieta tipoDieta) {

        String sql = """
                INSERT INTO tipos_dieta
                (nombre, descripcion)
                VALUES (?, ?)
                """;

        try (
                Connection conn = ConexionBD.conectar();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setString(1, tipoDieta.getNombre());
            ps.setString(2, tipoDieta.getDescripcion());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void modificar(TipoDieta tipoDieta) {

        String sql = """
                UPDATE tipos_dieta
                SET nombre = ?,
                    descripcion = ?
                WHERE id = ?
                """;

        try (
                Connection conn = ConexionBD.conectar();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setString(1, tipoDieta.getNombre());
            ps.setString(2, tipoDieta.getDescripcion());
            ps.setLong(3, tipoDieta.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(Long id) {

        String sql = """
                DELETE FROM tipos_dieta
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
    public List<TipoDieta> listar() {

        List<TipoDieta> tipos = new ArrayList<>();

        String sql = """
                SELECT *
                FROM tipos_dieta
                ORDER BY nombre
                """;

        try (
                Connection conn = ConexionBD.conectar();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                TipoDieta tipo = new TipoDieta();

                tipo.setId(rs.getLong("id"));
                tipo.setNombre(rs.getString("nombre"));
                tipo.setDescripcion(rs.getString("descripcion"));

                tipos.add(tipo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tipos;
    }
}