package com.cuidados.paliativos.dao;

import com.cuidados.paliativos.modelo.Especialidad;
import com.cuidados.paliativos.persistencia.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EspecialidadDAOImpl implements EspecialidadDAO {
    @Override
    public List<Especialidad> listar() {
        List<Especialidad> especialidades = new ArrayList<>();

        String sql = """
                SELECT e.*
                FROM especialidades e
                ORDER BY e.nombre
                """;

        try (
                Connection conn = ConexionBD.conectar();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Especialidad especialidad = new Especialidad();

                especialidad.setId(rs.getLong("id"));
                especialidad.setNombre(rs.getString("nombre"));
                especialidad.setDescripcion(rs.getString("descripcion"));

                especialidades.add(especialidad);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return especialidades;
    }
}
