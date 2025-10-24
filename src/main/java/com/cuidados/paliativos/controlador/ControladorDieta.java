package com.cuidados.paliativos.controlador;

import com.cuidados.paliativos.modelo.Dieta;
import com.cuidados.paliativos.modelo.TipoDieta;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;

public class ControladorDieta {

    @FXML
    private TextField txtNombreDieta;

    @FXML
    private TextArea txtDescripcion;

    @FXML
    private ComboBox<TipoDieta> cbTipoDieta;

    @FXML
    private TableView<Dieta> tablaDietas;

    @FXML
    private TableColumn<Dieta, Long> colId;

    @FXML
    private TableColumn<Dieta, String> colNombre;

    @FXML
    private TableColumn<Dieta, String> colDescripcion;

    @FXML
    private TableColumn<Dieta, String> colTipo;

    private final ObservableList<Dieta> listaDietas = FXCollections.observableArrayList();
    private final ObservableList<TipoDieta> listaTiposDieta = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        listaTiposDieta.addAll(
                new TipoDieta() {{ setId(1L); setNombre("Blanda"); setDescripcion("Comidas suaves"); }},
                new TipoDieta() {{ setId(2L); setNombre("Hipocalórica"); setDescripcion("Baja en calorías"); }},
                new TipoDieta() {{ setId(3L); setNombre("Vegetariana"); setDescripcion("Sin carne"); }},
                new TipoDieta() {{ setId(4L); setNombre("Sin sal"); setDescripcion("Baja en sodio"); }}
        );

        cbTipoDieta.setItems(listaTiposDieta);

        cbTipoDieta.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(TipoDieta item, boolean empty) {
                super.updateItem(item, empty);
                setText((empty || item == null) ? null : item.getNombre());
            }
        });
        cbTipoDieta.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(TipoDieta item, boolean empty) {
                super.updateItem(item, empty);
                setText((empty || item == null) ? null : item.getNombre());
            }
        });

        colId.setCellValueFactory(c -> new javafx.beans.property.SimpleLongProperty(c.getValue().getId()).asObject());
        colNombre.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getNombre()));
        colDescripcion.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getDescripcion()));
        colTipo.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(
                c.getValue().getTipoDieta() != null ? c.getValue().getTipoDieta().getNombre() : ""
        ));

        tablaDietas.setItems(listaDietas);
    }

    @FXML
    private void nuevaDieta() {
        limpiarCampos();
    }

    @FXML
    private void guardarDieta() {
        if (txtNombreDieta.getText().isEmpty() || txtDescripcion.getText().isEmpty() || cbTipoDieta.getValue() == null) {
            mostrarAlerta("Complete todos los campos antes de guardar.");
            return;
        }

        Dieta nueva = new Dieta(
                (long) (listaDietas.size() + 1),
                txtNombreDieta.getText(),
                txtDescripcion.getText(),
                cbTipoDieta.getValue()
        );
        listaDietas.add(nueva);
        limpiarCampos();
    }

    @FXML
    private void modificarDieta() {
        Dieta seleccionada = tablaDietas.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            seleccionada.setNombre(txtNombreDieta.getText());
            seleccionada.setDescripcion(txtDescripcion.getText());
            seleccionada.setTipoDieta(cbTipoDieta.getValue());
            tablaDietas.refresh();
            limpiarCampos();
        } else {
            mostrarAlerta("Seleccione una dieta para modificar.");
        }
    }

    @FXML
    private void eliminarDieta() {
        Dieta seleccionada = tablaDietas.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            listaDietas.remove(seleccionada);
        } else {
            mostrarAlerta("Seleccione una dieta para eliminar.");
        }
    }

    @FXML
    private void consultarDieta() {
        Dieta seleccionada = tablaDietas.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            txtNombreDieta.setText(seleccionada.getNombre());
            txtDescripcion.setText(seleccionada.getDescripcion());
            cbTipoDieta.setValue(seleccionada.getTipoDieta());
        } else {
            mostrarAlerta("Seleccione una dieta para consultar.");
        }
    }

    @FXML
    private void abrirDetalleDieta() {
        Dieta seleccionada = tablaDietas.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarAlerta("Seleccione una dieta para gestionar sus horarios.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cuidados/paliativos/vista/DetalleDieta.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Detalles de dieta: " + seleccionada.getNombre());
            stage.setScene(new Scene(loader.load()));

            ControladorDetalleDieta controladorDetalle = loader.getController();
            controladorDetalle.setDieta(seleccionada);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void limpiarCampos() {
        txtNombreDieta.clear();
        txtDescripcion.clear();
        cbTipoDieta.setValue(null);
    }

    private void mostrarAlerta(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Atención");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
