package com.cuidados.paliativos.dao;

import com.cuidados.paliativos.persistencia.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PlanMedicamentoDAOImpl implements PlanMedicamentoDAO {
    @Override
    public void guardar(Long idPlan, Long idMedicamento) {
        String sql = """
                INSERT INTO plan_medicamento
                (id_plan, id_medicamento)
                VALUES (?, ?)
                """;

        try (
                Connection conn = ConexionBD.conectar();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setLong(1, idPlan);
            ps.setLong(2, idMedicamento);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(Long idPlan, Long idMedicamento) {
        String sql = """
                DELETE FROM plan_medicamento
                WHERE id_plan = ? AND id_medicamento = ?
                """;

        try (
                Connection conn = ConexionBD.conectar();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setLong(1, idPlan);
            ps.setLong(2, idMedicamento);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarPorPlan(Long idPlan) {

        String sql = """
            DELETE FROM plan_medicamento
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
