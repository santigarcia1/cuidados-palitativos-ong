package com.cuidados.paliativos.controlador;

import com.cuidados.paliativos.MainApp;
import com.cuidados.paliativos.modelo.Usuario;
import com.cuidados.paliativos.seguridad.Permisos;
import com.cuidados.paliativos.seguridad.Sesion;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ControladorMenu {

    @FXML
    private Button btnPacientes;

    @FXML
    private Button btnProfesionales;

    @FXML
    private Button btnVoluntarios;

    @FXML
    private Button btnPlanes;

    @FXML
    private Button btnDietas;

    @FXML
    private Button btnMedicamentos;

    @FXML
    private void initialize() {

        Usuario usuario = Sesion.getUsuarioLogueado();

        if (usuario == null) {
            return;
        }

        /*
         * ADMINISTRADOR
         * Ve todo
         */
        if (Permisos.esAdministrador(usuario)) {
            return;
        }

        /*
         * ADMINISTRATIVO
         */
        if (Permisos.esAdministrativo(usuario)) {

            btnPlanes.setDisable(true);
            btnDietas.setDisable(true);
            btnMedicamentos.setDisable(true);

            return;
        }

        /*
         * PROFESIONAL
         */
        if (Permisos.esProfesional(usuario)) {

            btnProfesionales.setDisable(true);
            btnVoluntarios.setDisable(true);

            return;
        }

        /*
         * VOLUNTARIO
         */
        if (Permisos.esVoluntario(usuario)) {

            btnProfesionales.setDisable(true);
            btnVoluntarios.setDisable(true);
            btnMedicamentos.setDisable(true);
            btnDietas.setDisable(true);

        }
    }

    private void abrir(String fxml, String titulo) throws Exception {

        FXMLLoader loader =
                new FXMLLoader(
                        getClass().getResource(
                                "/com/cuidados/paliativos/vista/" + fxml
                        ));

        Stage st = new Stage();

        st.setTitle(titulo);
        st.setScene(new Scene(loader.load()));
        st.show();
    }

    private void accesoDenegado() {

        Alert alert = new Alert(Alert.AlertType.WARNING);

        alert.setTitle("Acceso denegado");
        alert.setHeaderText(null);
        alert.setContentText(
                "No tiene permisos para acceder a esta funcionalidad."
        );

        alert.showAndWait();
    }

    @FXML
    private void abrirPacientes() throws Exception {

        abrir("pacientes.fxml", "Pacientes");
    }

    @FXML
    private void abrirProfesionales() throws Exception {

        Usuario usuario = Sesion.getUsuarioLogueado();

        if (Permisos.esProfesional(usuario)
                || Permisos.esVoluntario(usuario)) {

            accesoDenegado();
            return;
        }

        abrir("profesionales.fxml", "Profesionales");
    }

    @FXML
    private void abrirVoluntarios() throws Exception {

        Usuario usuario = Sesion.getUsuarioLogueado();

        if (Permisos.esProfesional(usuario)
                || Permisos.esVoluntario(usuario)) {

            accesoDenegado();
            return;
        }

        abrir("voluntarios.fxml", "Voluntarios");
    }

    @FXML
    private void abrirPlanes() throws Exception {

        Usuario usuario = Sesion.getUsuarioLogueado();

        if (Permisos.esAdministrativo(usuario)) {

            accesoDenegado();
            return;
        }

        abrir("planes-de-cuidado.fxml", "Planes de cuidado");
    }

    @FXML
    private void abrirDietas() throws Exception {

        Usuario usuario = Sesion.getUsuarioLogueado();

        if (Permisos.esAdministrativo(usuario)) {

            accesoDenegado();
            return;
        }

        abrir("dietas.fxml", "Dietas");
    }

    @FXML
    private void abrirMedicamentos() throws Exception {

        Usuario usuario = Sesion.getUsuarioLogueado();

        if (Permisos.esAdministrativo(usuario)
                || Permisos.esVoluntario(usuario)) {

            accesoDenegado();
            return;
        }

        abrir("medicamentos.fxml", "Medicamentos");
    }

    @FXML
    private void cerrarSesion() throws Exception {

        Sesion.cerrarSesion();

        MainApp.showLogin();
    }
}