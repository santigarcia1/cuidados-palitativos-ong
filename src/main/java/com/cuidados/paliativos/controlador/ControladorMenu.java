package com.cuidados.paliativos.controlador;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ControladorMenu {
    private void abrir(String fxml, String titulo) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cuidados/paliativos/vista/" + fxml));
        Stage st = new Stage();
        st.setTitle(titulo);
        st.setScene(new Scene(loader.load()));
        st.show();
    }
    @FXML private void abrirPacientes() throws Exception { abrir("pacientes.fxml","Pacientes"); }
    @FXML private void abrirProfesionales() throws Exception { abrir("profesionales.fxml","Profesionales"); }
    @FXML private void abrirVoluntarios() throws Exception { abrir("voluntarios.fxml","Voluntarios"); }
    @FXML private void abrirPlanes() throws Exception { abrir("planes-de-cuidado.fxml","Planes de cuidado"); }
    @FXML private void abrirDietas() throws Exception { abrir("dietas.fxml","Dietas"); }
}
