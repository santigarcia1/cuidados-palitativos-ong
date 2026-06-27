package com.cuidados.paliativos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    private static Stage mainStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainStage = primaryStage;
        primaryStage.setTitle("Manos Abiertas - Sistema de Gestión de Cuidados Paliativos");
        showLogin(primaryStage);
        primaryStage.show();
    }

    public static void showLogin(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                MainApp.class.getResource("/com/cuidados/paliativos/vista/login.fxml"));

        Scene scene = new Scene(loader.load());

        stage.setScene(scene);
        stage.setTitle("Inicio de sesión");
        stage.show();
    }

    public static void showMainMenu() throws Exception {
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/com/cuidados/paliativos/vista/menu-principal.fxml"));
        Scene scene = new Scene(loader.load());
        mainStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch();
    }
}

