package com.cuidados.paliativos.dao;

import com.cuidados.paliativos.modelo.Paciente;
import com.cuidados.paliativos.modelo.PlanCuidado;
import com.cuidados.paliativos.modelo.Profesional;
import com.cuidados.paliativos.persistencia.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlanCuidadoDAOImpl implements PlanCuidadoDAO {

    @Override
    public void guardar(PlanCuidado plan) {

        String sql = """
                INSERT INTO planes_cuidado
                (fecha_creacion, observaciones, id_paciente, id_profesional)
                VALUES (?, ?, ?, ?)
                """;

        try (
                Connection conn = ConexionBD.conectar();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setDate(1, Date.valueOf(plan.getFechaCreacion()));
            ps.setString(2, plan.getObservaciones());
            ps.setLong(3, plan.getPaciente().getId());
            ps.setLong(4, plan.getProfesional().getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void modificar(PlanCuidado plan) {

        String sql = """
                UPDATE planes_cuidado
                SET fecha_creacion = ?,
                    observaciones = ?,
                    id_paciente = ?,
                    id_profesional = ?
                WHERE id = ?
                """;

        try (
                Connection conn = ConexionBD.conectar();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setDate(1, Date.valueOf(plan.getFechaCreacion()));
            ps.setString(2, plan.getObservaciones());
            ps.setLong(3, plan.getPaciente().getId());
            ps.setLong(4, plan.getProfesional().getId());
            ps.setLong(5, plan.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void eliminar(Long id) {

        String sql = "DELETE FROM planes_cuidado WHERE id = ?";

        try (
                Connection conn = ConexionBD.conectar();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setLong(1, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<PlanCuidado> listar() {

        List<PlanCuidado> lista = new ArrayList<>();

        String sql = """
                SELECT
                    pc.id,
                    pc.fecha_creacion,
                    pc.observaciones,
                    p.id id_paciente,
                    p.nombre nombre_paciente,
                    p.apellido apellido_paciente,
                    pr.id id_profesional,
                    pr.nombre nombre_profesional,
                    pr.apellido apellido_profesional
                FROM planes_cuidado pc
                INNER JOIN pacientes p
                    ON pc.id_paciente = p.id
                INNER JOIN profesionales pr
                    ON pc.id_profesional = pr.id
                ORDER BY pc.id
                """;

        try (
                Connection conn = ConexionBD.conectar();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Paciente paciente = new Paciente();
                paciente.setId(rs.getLong("id_paciente"));
                paciente.setNombre(rs.getString("nombre_paciente"));
                paciente.setApellido(rs.getString("apellido_paciente"));

                Profesional profesional = new Profesional();
                profesional.setId(rs.getLong("id_profesional"));
                profesional.setNombre(rs.getString("nombre_profesional"));
                profesional.setApellido(rs.getString("apellido_profesional"));

                PlanCuidado plan = new PlanCuidado();

                plan.setId(rs.getLong("id"));
                plan.setFechaCreacion(
                        rs.getDate("fecha_creacion").toLocalDate()
                );
                plan.setObservaciones(
                        rs.getString("observaciones")
                );

                plan.setPaciente(paciente);
                plan.setProfesional(profesional);

                lista.add(plan);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }
}