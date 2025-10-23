package com.cuidados.paliativos.controlador;

import com.cuidados.paliativos.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ControladorLogin {
    @FXML private TextField txtEmail;
    @FXML private PasswordField txtPassword;
    @FXML private Label lblError;

    @FXML
    private void onLogin() {
        String email = txtEmail.getText();
        String pw = txtPassword.getText();

        if ("admin@manosabiertas.org".equals(email) && "admin".equals(pw)) {
            try { MainApp.showMainMenu(); }
            catch (Exception e) { e.printStackTrace(); }
        } else {
            lblError.setText("Credenciales incorrectas.");
        }
    }
}
