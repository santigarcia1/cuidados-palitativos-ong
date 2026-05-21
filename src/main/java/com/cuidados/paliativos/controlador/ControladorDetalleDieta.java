package com.cuidados.paliativos.controlador;

import com.cuidados.paliativos.modelo.Dieta;
import com.cuidados.paliativos.modelo.DetalleDieta;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ControladorDetalleDieta {

    @FXML
    private ComboBox<DetalleDieta.Horario> cbHorario;

    @FXML
    private TextArea txtDescripcion;

    @FXML
    private TableView<DetalleDieta> tablaDetalles;

    @FXML
    private TableColumn<DetalleDieta, String> colHorario;

    @FXML
    private TableColumn<DetalleDieta, String> colDescripcion;

    private final ObservableList<DetalleDieta> listaDetalles = FXCollections.observableArrayList();

    private Dieta dietaSeleccionada;

    @FXML
    private void initialize() {
        cbHorario.setItems(FXCollections.observableArrayList(DetalleDieta.Horario.values()));

        colHorario.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getHorario().name()));
        colDescripcion.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getDescripcion()));

        tablaDetalles.setItems(listaDetalles);
    }

    public void setDieta(Dieta dieta) {
        this.dietaSeleccionada = dieta;
        if (dieta.getDetalles() != null) {
            listaDetalles.setAll(dieta.getDetalles());
        } else {
            dieta.setDetalles(FXCollections.observableArrayList());
        }
    }

    @FXML
    private void agregarDetalle() {
        if (cbHorario.getValue() == null || txtDescripcion.getText().isEmpty()) {
            mostrarAlerta("Seleccione un horario y agregue una descripción.");
            return;
        }

        DetalleDieta nuevo = new DetalleDieta(
                (long) (listaDetalles.size() + 1),
                dietaSeleccionada,
                cbHorario.getValue(),
                txtDescripcion.getText()
        );

        listaDetalles.add(nuevo);
        dietaSeleccionada.getDetalles().add(nuevo);
        limpiarCampos();
    }

    @FXML
    private void eliminarDetalle() {
        DetalleDieta seleccionado = tablaDetalles.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            listaDetalles.remove(seleccionado);
            dietaSeleccionada.getDetalles().remove(seleccionado);
        } else {
            mostrarAlerta("Seleccione un detalle para eliminar.");
        }
    }

    private void limpiarCampos() {
        cbHorario.setValue(null);
        txtDescripcion.clear();
    }

    private void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Atención");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
