package com.cuidados.paliativos.controlador;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Controlador para la pantalla de gestión de planes de cuidado.
 * Permite crear, editar y eliminar planes, así como asociar medicamentos y dietas.
 *
 * Autor: Santiago García
 */
public class ControladorPlanesCuidado {

    // --- Componentes de interfaz ---
    @FXML private ComboBox<String> cbPacientes;
    @FXML private ComboBox<String> cbProfesionales;
    @FXML private DatePicker dpFechaCreacion;
    @FXML private TextArea txtObservaciones;
    @FXML private ComboBox<String> cbMedicamentos;
    @FXML private ComboBox<String> cbDietas;

    @FXML private TableView<PlanCuidadoDTO> tablaPlanes;
    @FXML private TableColumn<PlanCuidadoDTO, String> colPaciente;
    @FXML private TableColumn<PlanCuidadoDTO, String> colProfesional;
    @FXML private TableColumn<PlanCuidadoDTO, String> colFecha;
    @FXML private TableColumn<PlanCuidadoDTO, String> colObservaciones;

    // --- Datos simulados ---
    private final ObservableList<String> pacientes = FXCollections.observableArrayList("Juan Pérez", "María Gómez");
    private final ObservableList<String> profesionales = FXCollections.observableArrayList("Dr. López", "Dra. Martínez");
    private final ObservableList<String> medicamentos = FXCollections.observableArrayList("Paracetamol", "Ibuprofeno");
    private final ObservableList<String> dietas = FXCollections.observableArrayList("Blanda", "Hipocalórica");

    private final ObservableList<PlanCuidadoDTO> listaPlanes = FXCollections.observableArrayList();

    // --- Inicialización ---
    @FXML
    public void initialize() {
        cbPacientes.setItems(pacientes);
        cbProfesionales.setItems(profesionales);
        cbMedicamentos.setItems(medicamentos);
        cbDietas.setItems(dietas);

        dpFechaCreacion.setValue(LocalDate.now());

        // Configurar columnas de tabla
        colPaciente.setCellValueFactory(cellData -> cellData.getValue().pacienteProperty());
        colProfesional.setCellValueFactory(cellData -> cellData.getValue().profesionalProperty());
        colFecha.setCellValueFactory(cellData -> cellData.getValue().fechaProperty());
        colObservaciones.setCellValueFactory(cellData -> cellData.getValue().observacionesProperty());

        tablaPlanes.setItems(listaPlanes);
    }

    // --- Acciones de botones ---
    @FXML
    private void nuevoPlan() {
        cbPacientes.getSelectionModel().clearSelection();
        cbProfesionales.getSelectionModel().clearSelection();
        cbMedicamentos.getSelectionModel().clearSelection();
        cbDietas.getSelectionModel().clearSelection();
        txtObservaciones.clear();
        dpFechaCreacion.setValue(LocalDate.now());
    }

    @FXML
    private void guardarPlan() {
        String paciente = cbPacientes.getValue();
        String profesional = cbProfesionales.getValue();
        String fecha = dpFechaCreacion.getValue().toString();
        String observaciones = txtObservaciones.getText();

        if (paciente == null || profesional == null) {
            mostrarAlerta("Debe seleccionar un paciente y un profesional.", Alert.AlertType.WARNING);
            return;
        }

        listaPlanes.add(new PlanCuidadoDTO(paciente, profesional, fecha, observaciones));
        mostrarAlerta("Plan de cuidado guardado exitosamente.", Alert.AlertType.INFORMATION);
        nuevoPlan();
    }

    @FXML
    private void eliminarPlan() {
        PlanCuidadoDTO seleccionado = tablaPlanes.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            listaPlanes.remove(seleccionado);
            mostrarAlerta("Plan eliminado correctamente.", Alert.AlertType.INFORMATION);
        } else {
            mostrarAlerta("Debe seleccionar un plan para eliminar.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void crearNuevoMedicamento() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cuidados/paliativos/vista/medicamentos.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Gestión de Medicamentos");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void crearNuevaDieta() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cuidados/paliativos/vista/dietas.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Gestión de Dietas");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // --- Utilidad ---
    private void mostrarAlerta(String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // --- DTO temporal para la tabla ---
    public static class PlanCuidadoDTO {
        private final javafx.beans.property.SimpleStringProperty paciente;
        private final javafx.beans.property.SimpleStringProperty profesional;
        private final javafx.beans.property.SimpleStringProperty fecha;
        private final javafx.beans.property.SimpleStringProperty observaciones;

        public PlanCuidadoDTO(String paciente, String profesional, String fecha, String observaciones) {
            this.paciente = new javafx.beans.property.SimpleStringProperty(paciente);
            this.profesional = new javafx.beans.property.SimpleStringProperty(profesional);
            this.fecha = new javafx.beans.property.SimpleStringProperty(fecha);
            this.observaciones = new javafx.beans.property.SimpleStringProperty(observaciones);
        }

        public javafx.beans.property.StringProperty pacienteProperty() { return paciente; }
        public javafx.beans.property.StringProperty profesionalProperty() { return profesional; }
        public javafx.beans.property.StringProperty fechaProperty() { return fecha; }
        public javafx.beans.property.StringProperty observacionesProperty() { return observaciones; }
    }
}
