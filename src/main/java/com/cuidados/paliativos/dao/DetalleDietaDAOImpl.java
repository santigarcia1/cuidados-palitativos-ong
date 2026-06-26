package com.cuidados.paliativos.dao;

import com.cuidados.paliativos.modelo.DetalleDieta;
import com.cuidados.paliativos.modelo.Dieta;
import com.cuidados.paliativos.persistencia.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DetalleDietaDAOImpl implements DetalleDietaDAO {

    @Override
    public void guardar(DetalleDieta detalle) {

        String sql = """
                INSERT INTO detalle_dieta
                (id_dieta, horario, descripcion)
                VALUES (?, ?, ?)
                """;

        try (
                Connection conn = ConexionBD.conectar();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setLong(1, detalle.getDieta().getId());
            ps.setString(2, detalle.getHorario());
            ps.setString(3, detalle.getDescripcion());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void modificar(DetalleDieta detalle) {

        String sql = """
                UPDATE detalle_dieta
                SET horario = ?,
                    descripcion = ?
                WHERE id = ?
                """;

        try (
                Connection conn = ConexionBD.conectar();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setString(1, detalle.getHorario());
            ps.setString(2, detalle.getDescripcion());
            ps.setLong(3, detalle.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(Long id) {

        String sql = "DELETE FROM detalle_dieta WHERE id = ?";

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
    public List<DetalleDieta> listarPorDieta(Long idDieta) {

        List<DetalleDieta> detalles = new ArrayList<>();

        String sql = """
                SELECT *
                FROM detalle_dieta
                WHERE id_dieta = ?
                ORDER BY id
                """;

        try (
                Connection conn = ConexionBD.conectar();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setLong(1, idDieta);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Dieta dieta = new Dieta();
                dieta.setId(rs.getLong("id_dieta"));

                DetalleDieta detalle = new DetalleDieta();

                detalle.setId(rs.getLong("id"));
                detalle.setDieta(dieta);

                detalle.setHorario(rs.getString("horario"));

                detalle.setDescripcion(
                        rs.getString("descripcion")
                );

                detalles.add(detalle);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return detalles;
    }
}