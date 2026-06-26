package com.cuidados.paliativos.dao;

import com.cuidados.paliativos.modelo.Frecuencia;
import com.cuidados.paliativos.modelo.Medicamento;
import com.cuidados.paliativos.persistencia.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicamentoDAOImpl implements MedicamentoDAO {

    @Override
    public Long guardar(Medicamento medicamento) {

        String sql = """
            INSERT INTO medicamentos
            (nombre, dosis, id_frecuencia)
            VALUES (?, ?, ?)
            """;

        try (
                Connection conn = ConexionBD.conectar();
                PreparedStatement ps = conn.prepareStatement(
                        sql,
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {

            ps.setString(1, medicamento.getNombre());
            ps.setString(2, medicamento.getDosis());
            ps.setLong(3, medicamento.getFrecuencia().getId());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                return rs.getLong(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void modificar(Medicamento medicamento) {

        String sql = """
                UPDATE medicamentos
                SET nombre = ?,
                    dosis = ?,
                    id_frecuencia = ?
                WHERE id = ?
                """;

        try (
                Connection conn = ConexionBD.conectar();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setString(1, medicamento.getNombre());
            ps.setString(2, medicamento.getDosis());
            ps.setLong(
                    3,
                    medicamento.getFrecuencia().getId()
            );
            ps.setLong(4, medicamento.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(Long id) {

        String sql = """
                DELETE FROM medicamentos
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
    public List<Medicamento> listar() {
        List<Medicamento> medicamentos = new ArrayList<>();

        String sql = """
                SELECT m.*,
                       f.descripcion
                FROM medicamentos m
                INNER JOIN frecuencias f
                    ON m.id_frecuencia = f.id
                ORDER BY m.id
                """;

        try (
                Connection conn = ConexionBD.conectar();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Frecuencia frecuencia = new Frecuencia();

                frecuencia.setId(
                        rs.getLong("id_frecuencia")
                );

                frecuencia.setDescripcion(
                        rs.getString("descripcion")
                );

                Medicamento medicamento =
                        new Medicamento();

                medicamento.setId(
                        rs.getLong("id")
                );

                medicamento.setNombre(
                        rs.getString("nombre")
                );

                medicamento.setDosis(
                        rs.getString("dosis")
                );

                medicamento.setFrecuencia(
                        frecuencia
                );

                medicamentos.add(medicamento);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return medicamentos;
    }

    @Override
    public List<Medicamento> buscarPorIdDePlanCuidado(Long idPlanCuidado) {
        List<Medicamento> medicamentos = new ArrayList<>();

        String sql = """
                SELECT m.*,
                       f.descripcion
                FROM medicamentos m
                INNER JOIN frecuencias f
                    ON m.id_frecuencia = f.id
                INNER JOIN plan_medicamento pm 
                    ON m.id = pm.id_medicamento
                WHERE pm.id_plan = ?
                ORDER BY m.id
                """;

        try (
                Connection conn = ConexionBD.conectar();
                PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            ps.setLong(1, idPlanCuidado);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Frecuencia frecuencia = new Frecuencia();

                frecuencia.setId(
                        rs.getLong("id_frecuencia")
                );

                frecuencia.setDescripcion(
                        rs.getString("descripcion")
                );

                Medicamento medicamento =
                        new Medicamento();

                medicamento.setId(
                        rs.getLong("id")
                );

                medicamento.setNombre(
                        rs.getString("nombre")
                );

                medicamento.setDosis(
                        rs.getString("dosis")
                );

                medicamento.setFrecuencia(
                        frecuencia
                );

                medicamentos.add(medicamento);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return medicamentos;
    }
}