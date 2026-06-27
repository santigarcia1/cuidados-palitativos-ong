package com.cuidados.paliativos.dao;

import com.cuidados.paliativos.modelo.PlanDieta;
import com.cuidados.paliativos.persistencia.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PlanDietaImpl implements PlanDietaDAO {
    @Override
    public void guardar(Long idPlan, Long idDieta) {
        String sql = """
                INSERT INTO plan_dieta
                (id_plan, id_dieta)
                VALUES (?, ?)
                """;

        try (
                Connection conn = ConexionBD.conectar();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setLong(1, idPlan);
            ps.setLong(2, idDieta);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(Long idPlan, Long idDieta) {
        String sql = """
                DELETE FROM plan_dieta
                WHERE id_plan = ? AND id_dieta = ?
                """;

        try (
                Connection conn = ConexionBD.conectar();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setLong(1, idPlan);
            ps.setLong(2, idDieta);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarPorPlan(Long idPlan) {

        String sql = """
            DELETE FROM plan_dieta
            WHERE id_plan = ?
            """;

        try (
                Connection conn = ConexionBD.conectar();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setLong(1, idPlan);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
