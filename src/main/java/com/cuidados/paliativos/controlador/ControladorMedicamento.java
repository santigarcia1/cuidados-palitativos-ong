package com.cuidados.paliativos.controlador;

import com.cuidados.paliativos.modelo.Medicamento;
import com.cuidados.paliativos.modelo.Frecuencia;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ControladorMedicamento {

    @FXML
    private TextField txtNombreMedicamento;

    @FXML
    private TextField txtDosis;

    @FXML
    private ComboBox<Frecuencia> cbFrecuencia;

    @FXML
    private TableView<Medicamento> tablaMedicamentos;

    @FXML
    private TableColumn<Medicamento, Long> colId;

    @FXML
    private TableColumn<Medicamento, String> colNombre;

    @FXML
    private TableColumn<Medicamento, String> colDosis;

    @FXML
    private TableColumn<Medicamento, String> colFrecuencia;

    private final ObservableList<Medicamento> listaMedicamentos = FXCollections.observableArrayList();
    private final ObservableList<Frecuencia> listaFrecuencias = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Frecuencias de ejemplo (podrían venir de BD)
        listaFrecuencias.addAll(
                new Frecuencia(1L, "Diaria"),
                new Frecuencia(2L, "Semanal"),
                new Frecuencia(3L, "Mensual")
        );

        cbFrecuencia.setItems(listaFrecuencias);

        // Mostrar descripción en el ComboBox
        cbFrecuencia.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Frecuencia item, boolean empty) {
                super.updateItem(item, empty);
                setText((empty || item == null) ? null : item.getDescripcion());
            }
        });
        cbFrecuencia.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Frecuencia item, boolean empty) {
                super.updateItem(item, empty);
                setText((empty || item == null) ? null : item.getDescripcion());
            }
        });

        // Configurar columnas de tabla
        colId.setCellValueFactory(c -> new javafx.beans.property.SimpleLongProperty(c.getValue().getId()).asObject());
        colNombre.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getNombre()));
        colDosis.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getDosis()));
        colFrecuencia.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(
                c.getValue().getFrecuencia() != null ? c.getValue().getFrecuencia().getDescripcion() : ""
        ));

        tablaMedicamentos.setItems(listaMedicamentos);
    }

    @FXML
    private void nuevoMedicamento() {
        limpiarCampos();
    }

    @FXML
    private void guardarMedicamento() {
        if (txtNombreMedicamento.getText().isEmpty() ||
                txtDosis.getText().isEmpty() ||
                cbFrecuencia.getValue() == null) {
            mostrarAlerta("Complete todos los campos antes de guardar.");
            return;
        }

        Medicamento nuevo = new Medicamento(
                txtNombreMedicamento.getText(),
                txtDosis.getText(),
                cbFrecuencia.getValue()
        );
        nuevo.setId((long) (listaMedicamentos.size() + 1));

        listaMedicamentos.add(nuevo);
        limpiarCampos();
    }

    @FXML
    private void modificarMedicamento() {
        Medicamento seleccionado = tablaMedicamentos.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            seleccionado.setNombre(txtNombreMedicamento.getText());
            seleccionado.setDosis(txtDosis.getText());
            seleccionado.setFrecuencia(cbFrecuencia.getValue());
            tablaMedicamentos.refresh();
            limpiarCampos();
        } else {
            mostrarAlerta("Seleccione un medicamento para modificar.");
        }
    }

    @FXML
    private void eliminarMedicamento() {
        Medicamento seleccionado = tablaMedicamentos.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            listaMedicamentos.remove(seleccionado);
        } else {
            mostrarAlerta("Seleccione un medicamento para eliminar.");
        }
    }

    @FXML
    private void consultarMedicamento() {
        Medicamento seleccionado = tablaMedicamentos.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            txtNombreMedicamento.setText(seleccionado.getNombre());
            txtDosis.setText(seleccionado.getDosis());
            cbFrecuencia.setValue(seleccionado.getFrecuencia());
        } else {
            mostrarAlerta("Seleccione un medicamento para consultar.");
        }
    }

    private void limpiarCampos() {
        txtNombreMedicamento.clear();
        txtDosis.clear();
        cbFrecuencia.setValue(null);
    }

    private void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Atención");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
