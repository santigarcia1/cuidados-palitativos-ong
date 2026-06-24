package com.cuidados.paliativos.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static final String URL =
            "jdbc:mysql://localhost:3306/cuidados_paliativos";

    private static final String USER =
            "root";

    private static final String PASSWORD =
            "root";

    public static Connection conectar()
            throws SQLException {

        return DriverManager.getConnection(
                URL,
                USER,
                PASSWORD
        );
    }
}