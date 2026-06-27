package com.cuidados.paliativos.controlador;

import com.cuidados.paliativos.dao.*;
import com.cuidados.paliativos.modelo.*;
import com.cuidados.paliativos.seguridad.Permisos;
import com.cuidados.paliativos.seguridad.Sesion;

import javafx.beans.property.SimpleStringProperty;
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
import java.util.Optional;

/**
 * Controlador para la pantalla de gestión de planes de cuidado.
 * Permite crear, editar y eliminar planes, así como asociar medicamentos y dietas.
 *
 * Autor: Santiago García
 */
public class ControladorPlanesCuidado {

    // --- Componentes de interfaz ---
    @FXML private ComboBox<Paciente> cbPacientes;
    @FXML private ComboBox<Profesional> cbProfesionales;
    @FXML private DatePicker dpFechaCreacion;
    @FXML private TextArea txtObservaciones;

    @FXML private TableView<PlanCuidado> tablaPlanes;
    @FXML private TableColumn<PlanCuidado, String> colId;
    @FXML private TableColumn<PlanCuidado, String> colPaciente;
    @FXML private TableColumn<PlanCuidado, String> colProfesional;
    @FXML private TableColumn<PlanCuidado, String> colFecha;
    @FXML private TableColumn<PlanCuidado, String> colObservaciones;

    @FXML
    private Button btnNuevo;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnModificar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnMedicamentos;

    @FXML
    private Button btnDietas;

    // --- Datos simulados ---
    private final ObservableList<Paciente> listaPacientes = FXCollections.observableArrayList();
    private final ObservableList<Profesional> listaProfesionales = FXCollections.observableArrayList();
    private final ObservableList<Medicamento> listaMedicamentos = FXCollections.observableArrayList();
    private final ObservableList<Dieta> listaDietas = FXCollections.observableArrayList();

    private final ObservableList<PlanCuidado> listaPlanes = FXCollections.observableArrayList();

    private final PlanCuidadoDAO planCuidadoDAO = new PlanCuidadoDAOImpl();

    private final PacienteDAO pacienteDAO = new PacienteDAOImpl();

    private final ProfesionalDAO profesionalDAO = new ProfesionalDAOImpl();

    private final MedicamentoDAO medicamentoDAO = new MedicamentoDAOImpl();

    private final DietaDAO dietaDAO = new DietaDAOImpl();

    // --- Inicialización ---
    @FXML
    public void initialize() {
        configurarPermisos();

        // Configurar columnas de tabla

        colId.setCellValueFactory(c ->
                new SimpleStringProperty(
                        c.getValue().getId().toString()
                ));

        colPaciente.setCellValueFactory(c ->
                new SimpleStringProperty(
                        c.getValue().getPaciente().getNombre() + " " + c.getValue().getPaciente().getApellido()
                ));

        colProfesional.setCellValueFactory(c ->
                new SimpleStringProperty(
                        c.getValue().getProfesional().getNombre() + " " + c.getValue().getProfesional().getApellido()
                ));

        colFecha.setCellValueFactory(c ->
                new SimpleStringProperty(
                        c.getValue().getFechaCreacion() != null
                                ? c.getValue().getFechaCreacion().toString()
                                : ""
                ));

        colObservaciones.setCellValueFactory(c ->
                new SimpleStringProperty(
                        c.getValue().getObservaciones()
                ));

        cbPacientes.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Paciente item, boolean empty) {
                super.updateItem(item, empty);

                setText(
                        empty || item == null
                                ? null
                                : item.getNombre() + " " + item.getApellido()
                );
            }
        });

        cbProfesionales.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Profesional item, boolean empty) {
                super.updateItem(item, empty);

                setText(
                        empty || item == null
                                ? null
                                : item.getNombre() + " " + item.getApellido()
                );
            }
        });

        cbProfesionales.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Profesional item, boolean empty) {
                super.updateItem(item, empty);

                setText(
                        empty || item == null
                                ? null
                                : item.getNombre() + " " + item.getApellido()
                );
            }
        });

        cbPacientes.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Paciente item, boolean empty) {
                super.updateItem(item, empty);

                setText(
                        empty || item == null
                                ? null
                                : item.getNombre() + " " + item.getApellido()
                );
            }
        });

        tablaPlanes.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, anterior, seleccionado) -> {
                    if (seleccionado != null) {

                        txtObservaciones.setText(seleccionado.getObservaciones());
                        cbPacientes.setValue(seleccionado.getPaciente());
                        cbProfesionales.setValue(seleccionado.getProfesional());
                        btnGuardar.setDisable(true);
                        if (seleccionado.getFechaCreacion() != null) {
                            dpFechaCreacion.setValue(
                                    seleccionado.getFechaCreacion()
                            );
                        }
                    }
                });

        cargarInfoInicial();
    }

    // --- Acciones de botones ---
    @FXML
    private void nuevoPlan() {
        if (!puedeGestionarPlanes()) {
            mostrarAlerta(
                    "No posee permisos para realizar esta acción.",
                    Alert.AlertType.WARNING
            );
            return;
        }
        limpiarCampos();
    }

    @FXML
    private void guardarPlan() {
        if (!puedeGestionarPlanes()) {
            mostrarAlerta(
                    "No posee permisos para realizar esta acción.",
                    Alert.AlertType.WARNING
            );
            return;
        }

        Paciente paciente = cbPacientes.getValue();
        Profesional profesional = cbProfesionales.getValue();
        String fecha = dpFechaCreacion.getValue().toString();
        String observaciones = txtObservaciones.getText();

        if (paciente == null || profesional == null) {
            mostrarAlerta("Debe seleccionar un paciente y un profesional.", Alert.AlertType.WARNING);
            return;
        }

        if (!confirmarAccion("¿Está seguro de guardar el plan de cuidado?")) {
            return;
        }

        PlanCuidado nuevoPlan = new PlanCuidado();
        nuevoPlan.setPaciente(paciente);
        nuevoPlan.setProfesional(profesional);
        nuevoPlan.setFechaCreacion(LocalDate.parse(fecha));
        nuevoPlan.setObservaciones(observaciones);

        planCuidadoDAO.guardar(nuevoPlan);

        cargarInfoInicial();

        mostrarAlerta("Plan de cuidado guardado exitosamente.", Alert.AlertType.INFORMATION);
        limpiarCampos();
    }

    @FXML
    private void modificarPlan() {
        if (!puedeGestionarPlanes()) {
            mostrarAlerta("No posee permisos para realizar esta acción.", Alert.AlertType.WARNING);
            return;
        }

        PlanCuidado seleccionado =
                tablaPlanes.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {
            if (!confirmarAccion("¿Está seguro de modificar el plan de cuidado?")) {
                return;
            }

            seleccionado.setObservaciones(txtObservaciones.getText());
            seleccionado.setPaciente(cbPacientes.getValue());
            seleccionado.setProfesional(cbProfesionales.getValue());
            seleccionado.setFechaCreacion(dpFechaCreacion.getValue());

            planCuidadoDAO.modificar(seleccionado);
            cargarInfoInicial();

            limpiarCampos();

        } else {
            mostrarAlerta("Seleccione un paciente.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void eliminarPlan() {
        PlanCuidado seleccionado = tablaPlanes.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            if (!confirmarAccion("¿Está seguro de eliminar el plan de cuidado?")) {
                return;
            }

            planCuidadoDAO.eliminar(seleccionado.getId());
            limpiarCampos();
            cargarInfoInicial();
            mostrarAlerta("Plan eliminado correctamente.", Alert.AlertType.INFORMATION);
        } else {
            mostrarAlerta("Debe seleccionar un plan para eliminar.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void abrirPantallaMedicamentos() {
        if (!puedeGestionarDietasOMedicamentos()) {
            return;
        }

        PlanCuidado seleccionado = tablaPlanes.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Seleccione un plan de cuidados para gestionar sus medicamentos.", Alert.AlertType.WARNING);
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cuidados/paliativos/vista/plan-medicamentos.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Medicamentos del plan de cuidado - Paciente: "
                    + seleccionado.getPaciente().getNombre() + " " + seleccionado.getPaciente().getApellido());
            stage.setScene(new Scene(loader.load()));

            ControladorPlanMedicamento controlador = loader.getController();

            controlador.setPlanCuidado(seleccionado);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirPantallaDietas() {
        if (!puedeGestionarDietasOMedicamentos()) {
            return;
        }

        PlanCuidado seleccionado = tablaPlanes.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Seleccione un plan de cuidados para gestionar sus dietas.", Alert.AlertType.WARNING);
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cuidados/paliativos/vista/plan-dietas.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Medicamentos del plan de cuidado - Paciente: "
                    + seleccionado.getPaciente().getNombre() + " " + seleccionado.getPaciente().getApellido());
            stage.setScene(new Scene(loader.load()));

            ControladorPlanDieta controlador = loader.getController();

            controlador.setPlanCuidado(seleccionado);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargarPlanes() {
        listaPlanes.clear();
        listaPlanes.addAll(planCuidadoDAO.listar());
        tablaPlanes.setItems(listaPlanes);
    }

    private void cargarProfesionales() {
        listaProfesionales.clear();
        listaProfesionales.addAll(profesionalDAO.listar());
        cbProfesionales.setItems(listaProfesionales);
    }

    private void cargarPacientes() {
        listaPacientes.clear();
        listaPacientes.addAll(pacienteDAO.listar());
        cbPacientes.setItems(listaPacientes);
    }

    private void cargarInfoInicial() {
        cargarPlanes();
        cargarProfesionales();
        cargarPacientes();
    }

    private void limpiarCampos() {
        txtObservaciones.clear();

        dpFechaCreacion.setValue(LocalDate.now());

        cbPacientes.setValue(null);
        cbProfesionales.setValue(null);

        btnGuardar.setDisable(false);
        tablaPlanes.getSelectionModel().clearSelection();
    }

    // --- Utilidad ---
    private void mostrarAlerta(String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
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

    private boolean puedeGestionarPlanes() {

        Usuario usuario = Sesion.getInstancia().getUsuarioLogueado();

        return Permisos.esAdministrador(usuario)
                || Permisos.esProfesional(usuario);
    }

    private boolean puedeGestionarDietasOMedicamentos() {
        Usuario usuario = Sesion.getInstancia().getUsuarioLogueado();

        return Permisos.esVoluntario(usuario)
                || Permisos.esProfesional(usuario)
                || Permisos.esAdministrador(usuario);
    }

    private void configurarPermisos() {

        Usuario usuario = Sesion.getInstancia().getUsuarioLogueado();

        if (usuario == null) {
            return;
        }

        if (!puedeGestionarPlanes()) {

            btnNuevo.setDisable(true);
            btnGuardar.setDisable(true);
            btnEliminar.setDisable(true);

            btnMedicamentos.setDisable(true);
            btnDietas.setDisable(true);

            cbPacientes.setDisable(true);
            cbProfesionales.setDisable(true);

            dpFechaCreacion.setDisable(true);

            txtObservaciones.setEditable(false);
        }
    }
}
