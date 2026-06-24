package com.cuidados.paliativos.dao;

import com.cuidados.paliativos.modelo.EstadoUsuario;
import com.cuidados.paliativos.modelo.Paciente;
import com.cuidados.paliativos.persistencia.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAOImpl
        implements PacienteDAO {

    @Override
    public void guardar(Paciente paciente) {

        String sql =
                "INSERT INTO pacientes " +
                        "(nombre,apellido,fecha_nacimiento,direccion,telefono,id_estado) " +
                        "VALUES (?,?,?,?,?,?)";

        try (
                Connection con =
                        ConexionBD.conectar();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(1,
                    paciente.getNombre());

            ps.setString(2,
                    paciente.getApellido());

            ps.setDate(
                    3,
                    new java.sql.Date(
                            paciente.getFechaNacimiento().getTime()
                    )
            );

            ps.setString(4,
                    paciente.getDireccion());

            ps.setString(5,
                    paciente.getTelefono());

            ps.setLong(
                    6,
                    paciente.getEstado().getId()
            );

            ps.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    @Override
    public void modificar(Paciente paciente) {

        String sql =
                "UPDATE pacientes " +
                        "SET nombre=?, apellido=?, fecha_nacimiento=?, direccion=?, telefono=?, id_estado=? " +
                        "WHERE id=?";

        try (
                Connection con =
                        ConexionBD.conectar();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(1,
                    paciente.getNombre());

            ps.setString(2,
                    paciente.getApellido());

            ps.setDate(
                    3,
                    new java.sql.Date(
                            paciente.getFechaNacimiento().getTime()
                    )
            );

            ps.setString(4,
                    paciente.getDireccion());

            ps.setString(5,
                    paciente.getTelefono());

            ps.setLong(
                    6,
                    paciente.getEstado().getId()
            );

            ps.setLong(
                    7,
                    paciente.getId()
            );

            ps.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(Long id) {

        String sql =
                "DELETE FROM pacientes WHERE id=?";

        try (
                Connection con =
                        ConexionBD.conectar();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setLong(1, id);

            ps.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    @Override
    public List<Paciente> listar() {

        List<Paciente> lista =
                new ArrayList<>();

        String sql =
                "SELECT p.*, " +
                        "e.id AS id_estado, " +
                        "e.nombre AS nombre_estado " +
                        "FROM pacientes p " +
                        "INNER JOIN estado_usuario e " +
                        "    ON p.id_estado = e.id " +
                        "ORDER BY p.id;";

        try (
                Connection con =
                        ConexionBD.conectar();

                Statement st =
                        con.createStatement();

                ResultSet rs =
                        st.executeQuery(sql)
        ) {

            while (rs.next()) {
                Paciente paciente = new Paciente();

                paciente.setId(rs.getLong("id"));
                paciente.setNombre(rs.getString("nombre"));
                paciente.setApellido(rs.getString("apellido"));
                paciente.setDireccion(rs.getString("direccion"));
                paciente.setTelefono(rs.getString("telefono"));
                paciente.setFechaNacimiento(rs.getDate("fecha_nacimiento"));

                EstadoUsuario estado = new EstadoUsuario();
                estado.setId(rs.getLong("id_estado"));
                estado.setNombre(rs.getString("nombre_estado"));

                paciente.setEstado(estado);

                lista.add(paciente);

            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return lista;
    }
}