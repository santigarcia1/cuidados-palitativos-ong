package com.cuidados.paliativos.controlador;

import com.cuidados.paliativos.MainApp;
import com.cuidados.paliativos.modelo.Rol;
import com.cuidados.paliativos.modelo.Usuario;
import com.cuidados.paliativos.seguridad.DatosPrueba;
import com.cuidados.paliativos.seguridad.SeguridadUtil;
import com.cuidados.paliativos.seguridad.Sesion;
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
        String password = txtPassword.getText();

        Usuario usuario = buscarUsuario(email);

        if (usuario != null &&
                SeguridadUtil.verificar(
                        password,
                        usuario.getContrasena())) {

            Sesion.getInstancia().setUsuarioLogueado(usuario);

            try {
                MainApp.showMainMenu();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            lblError.setText("Credenciales incorrectas.");
        }
    }

    private Usuario buscarUsuario(String email) {

        return DatosPrueba.getUsuarios()
                .stream()
                .filter(u ->
                        u.getEmail()
                                .equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }
}
