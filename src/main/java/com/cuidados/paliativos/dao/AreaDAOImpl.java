package com.cuidados.paliativos.dao;

import com.cuidados.paliativos.modelo.Area;
import com.cuidados.paliativos.persistencia.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AreaDAOImpl implements AreaDAO {

    @Override
    public void guardar(Area area) {

        String sql = """
                INSERT INTO areas
                (nombre, descripcion)
                VALUES (?, ?)
                """;

        try (
                Connection conn = ConexionBD.conectar();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setString(1, area.getNombre());
            ps.setString(2, area.getDescripcion());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void modificar(Area area) {

        String sql = """
                UPDATE areas
                SET nombre = ?,
                    descripcion = ?
                WHERE id = ?
                """;

        try (
                Connection conn = ConexionBD.conectar();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setString(1, area.getNombre());
            ps.setString(2, area.getDescripcion());
            ps.setLong(3, area.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(Long id) {

        String sql = """
                DELETE FROM areas
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
    public List<Area> listar() {

        List<Area> areas = new ArrayList<>();

        String sql = """
                SELECT *
                FROM areas
                ORDER BY nombre
                """;

        try (
                Connection conn = ConexionBD.conectar();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Area area = new Area();

                area.setId(rs.getLong("id"));
                area.setNombre(rs.getString("nombre"));
                area.setDescripcion(rs.getString("descripcion"));

                areas.add(area);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return areas;
    }
}