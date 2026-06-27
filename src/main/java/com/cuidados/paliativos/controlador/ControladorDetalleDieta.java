package com.cuidados.paliativos.controlador;

import com.cuidados.paliativos.dao.DetalleDietaDAO;
import com.cuidados.paliativos.dao.DetalleDietaDAOImpl;
import com.cuidados.paliativos.modelo.Dieta;
import com.cuidados.paliativos.modelo.DetalleDieta;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;
import java.util.Optional;

public class ControladorDetalleDieta {

    @FXML
    private ComboBox<String> cbHorario;

    @FXML
    private TextArea txtDescripcion;

    @FXML
    private TableView<DetalleDieta> tablaDetalles;

    @FXML
    private TableColumn<DetalleDieta, Long> colId;

    @FXML
    private TableColumn<DetalleDieta, String> colHorario;

    @FXML
    private TableColumn<DetalleDieta, String> colDescripcion;

    private final ObservableList<DetalleDieta> listaDetalles = FXCollections.observableArrayList();

    private final ObservableList<String> horarios = FXCollections.observableArrayList();

    private Dieta dietaSeleccionada;

    private DetalleDietaDAO detalleDietaDAO = new DetalleDietaDAOImpl();

    @FXML
    private void initialize() {
        colId.setCellValueFactory(c ->
                new SimpleObjectProperty<>(c.getValue().getId()));

        colHorario.setCellValueFactory(c ->
                new SimpleStringProperty(
                        c.getValue().getHorario()
                ));

        colDescripcion.setCellValueFactory(c ->
                new SimpleStringProperty(
                        c.getValue().getDescripcion()
                ));

        tablaDetalles.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, anterior, seleccionado) -> {
                    if (seleccionado != null) {
                        txtDescripcion.setText(seleccionado.getDescripcion());
                        cbHorario.setValue(seleccionado.getHorario());
                    }
                });
    }

    public void setDieta(Dieta dieta) {
        this.dietaSeleccionada = dieta;
        cargarDetalles();
        cargarHorarios();
    }

    @FXML
    private void nuevoDetalle() {
        limpiarCampos();
    }

    @FXML
    private void guardarDetalle() {
        if (cbHorario.getValue() == null || txtDescripcion.getText().isEmpty()) {
            mostrarAlerta("Seleccione un horario y agregue una descripción.");
            return;
        }

        if (!confirmarAccion("¿Está seguro de guardar el detalle de la dieta?")) {
            return;
        }

        DetalleDieta nuevo = new DetalleDieta();
        nuevo.setHorario(cbHorario.getValue());
        nuevo.setDescripcion(txtDescripcion.getText());
        nuevo.setDieta(dietaSeleccionada);

        detalleDietaDAO.guardar(nuevo);
        cargarDetalles();
        limpiarCampos();
    }

    @FXML
    private void modificarDetalle() {
        DetalleDieta seleccionado =
                tablaDetalles.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {
            if (!confirmarAccion("¿Está seguro de modificar el detalle de la dieta?")) {
                return;
            }
            seleccionado.setDescripcion(txtDescripcion.getText());
            seleccionado.setHorario(cbHorario.getValue());
            seleccionado.setDieta(dietaSeleccionada);

            detalleDietaDAO.modificar(seleccionado);
            cargarDetalles();
            limpiarCampos();
        }
    }

    @FXML
    private void eliminarDetalle() {
        DetalleDieta seleccionado = tablaDetalles.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            if (!confirmarAccion("¿Está seguro de eliminar el detalle de la dieta?")) {
                return;
            }
            detalleDietaDAO.eliminar(seleccionado.getId());
            cargarDetalles();
            limpiarCampos();
        } else {
            mostrarAlerta("Seleccione un detalle para eliminar.");
        }
    }

    private void cargarDetalles() {
        listaDetalles.clear();
        List<DetalleDieta> detalles = detalleDietaDAO.listarPorDieta(dietaSeleccionada.getId());
        listaDetalles.addAll(detalles);
        tablaDetalles.setItems(listaDetalles);
    }

    private void cargarHorarios() {
        horarios.addAll(
                listaDetalles.stream()
                        .map(DetalleDieta::getHorario)
                        .sorted()
                        .toList()
        );
        cbHorario.setItems(horarios);
    }

    private void limpiarCampos() {
        cbHorario.setValue(null);
        txtDescripcion.clear();
        tablaDetalles.getSelectionModel().clearSelection();
    }

    private void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Atención");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private boolean confirmarAccion(String mensaje) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Confirmación");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);

        Optional<ButtonType> resultado = alert.showAndWait();

        return resultado.isPresent()
                && resultado.get() == ButtonType.OK;
    }
}
