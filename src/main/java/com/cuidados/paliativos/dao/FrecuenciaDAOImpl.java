package com.cuidados.paliativos.dao;

import com.cuidados.paliativos.modelo.Frecuencia;
import com.cuidados.paliativos.persistencia.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FrecuenciaDAOImpl implements FrecuenciaDAO {

    @Override
    public void guardar(Frecuencia frecuencia) {

        String sql = """
                INSERT INTO frecuencias
                (descripcion)
                VALUES (?)
                """;

        try (
                Connection conn = ConexionBD.conectar();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setString(1, frecuencia.getDescripcion());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void modificar(Frecuencia frecuencia) {

        String sql = """
                UPDATE frecuencias
                SET descripcion = ?
                WHERE id = ?
                """;

        try (
                Connection conn = ConexionBD.conectar();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setString(1, frecuencia.getDescripcion());
            ps.setLong(2, frecuencia.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(Long id) {

        String sql = """
                DELETE FROM frecuencias
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
    public List<Frecuencia> listar() {

        List<Frecuencia> frecuencias = new ArrayList<>();

        String sql = """
                SELECT *
                FROM frecuencias
                ORDER BY descripcion
                """;

        try (
                Connection conn = ConexionBD.conectar();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Frecuencia frecuencia = new Frecuencia();

                frecuencia.setId(rs.getLong("id"));
                frecuencia.setDescripcion(
                        rs.getString("descripcion")
                );

                frecuencias.add(frecuencia);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return frecuencias;
    }
}