package com.cuidados.paliativos.controlador;

import com.cuidados.paliativos.modelo.EstadoUsuario;
import com.cuidados.paliativos.modelo.Paciente;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Date;
import java.time.LocalDate;

public class ControladorPacientes {

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtApellido;

    @FXML
    private DatePicker dpFechaNacimiento;

    @FXML
    private TextField txtDireccion;

    @FXML
    private TextField txtTelefono;

    @FXML
    private ComboBox<EstadoUsuario> cbEstado;

    @FXML
    private TableView<Paciente> tablaPacientes;

    @FXML
    private TableColumn<Paciente, Long> colId;

    @FXML
    private TableColumn<Paciente, String> colNombre;

    @FXML
    private TableColumn<Paciente, String> colApellido;

    @FXML
    private TableColumn<Paciente, String> colFechaNacimiento;

    @FXML
    private TableColumn<Paciente, String> colDireccion;

    @FXML
    private TableColumn<Paciente, String> colTelefono;

    @FXML
    private TableColumn<Paciente, String> colEstado;

    private final ObservableList<Paciente> listaPacientes =
            FXCollections.observableArrayList();

    private final ObservableList<EstadoUsuario> listaEstados =
            FXCollections.observableArrayList();

    @FXML
    private void initialize() {

        EstadoUsuario e1 = new EstadoUsuario();
        e1.setId(1L);
        e1.setNombre("Activo");

        EstadoUsuario e2 = new EstadoUsuario();
        e2.setId(2L);
        e2.setNombre("Inactivo");

        listaEstados.addAll(e1, e2);

        cbEstado.setItems(listaEstados);

        colId.setCellValueFactory(c ->
                new SimpleLongProperty(
                        c.getValue().getId()
                ).asObject());

        colNombre.setCellValueFactory(c ->
                new SimpleStringProperty(
                        c.getValue().getNombre()
                ));

        colApellido.setCellValueFactory(c ->
                new SimpleStringProperty(
                        c.getValue().getApellido()
                ));

        colFechaNacimiento.setCellValueFactory(c ->
                new SimpleStringProperty(
                        c.getValue().getFechaNacimiento() != null
                                ? c.getValue().getFechaNacimiento().toString()
                                : ""
                ));

        colDireccion.setCellValueFactory(c ->
                new SimpleStringProperty(
                        c.getValue().getDireccion()
                ));

        colTelefono.setCellValueFactory(c ->
                new SimpleStringProperty(
                        c.getValue().getTelefono()
                ));

        colEstado.setCellValueFactory(c ->
                new SimpleStringProperty(
                        c.getValue().getEstado() != null
                                ? c.getValue().getEstado().getNombre()
                                : ""
                ));

        tablaPacientes.setItems(listaPacientes);
    }

    @FXML
    private void nuevoPaciente() {
        limpiarCampos();
    }

    @FXML
    private void guardarPaciente() {

        if (txtNombre.getText().isEmpty() ||
                txtApellido.getText().isEmpty() ||
                dpFechaNacimiento.getValue() == null ||
                txtDireccion.getText().isEmpty() ||
                txtTelefono.getText().isEmpty() ||
                cbEstado.getValue() == null) {

            mostrarAlerta("Complete todos los campos.");
            return;
        }

        Paciente nuevo = new Paciente();

        nuevo.setId((long) (listaPacientes.size() + 1));
        nuevo.setNombre(txtNombre.getText());
        nuevo.setApellido(txtApellido.getText());

        LocalDate localDate = dpFechaNacimiento.getValue();
        nuevo.setFechaNacimiento(
                java.util.Date.from(
                        localDate.atStartOfDay(
                                java.time.ZoneId.systemDefault()
                        ).toInstant()
                )
        );

        nuevo.setDireccion(txtDireccion.getText());
        nuevo.setTelefono(txtTelefono.getText());
        nuevo.setEstado(cbEstado.getValue());

        listaPacientes.add(nuevo);

        limpiarCampos();
    }

    @FXML
    private void modificarPaciente() {

        Paciente seleccionado =
                tablaPacientes.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {

            seleccionado.setNombre(txtNombre.getText());
            seleccionado.setApellido(txtApellido.getText());

            LocalDate localDate = dpFechaNacimiento.getValue();
            seleccionado.setFechaNacimiento(
                    java.util.Date.from(
                            localDate.atStartOfDay(
                                    java.time.ZoneId.systemDefault()
                            ).toInstant()
                    )
            );

            seleccionado.setDireccion(txtDireccion.getText());
            seleccionado.setTelefono(txtTelefono.getText());
            seleccionado.setEstado(cbEstado.getValue());

            tablaPacientes.refresh();

            limpiarCampos();

        } else {
            mostrarAlerta("Seleccione un paciente.");
        }
    }

    @FXML
    private void eliminarPaciente() {

        Paciente seleccionado =
                tablaPacientes.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {

            listaPacientes.remove(seleccionado);

        } else {
            mostrarAlerta("Seleccione un paciente.");
        }
    }

    @FXML
    private void consultarPaciente() {

        Paciente seleccionado =
                tablaPacientes.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {

            txtNombre.setText(seleccionado.getNombre());
            txtApellido.setText(seleccionado.getApellido());

            if (seleccionado.getFechaNacimiento() != null) {

                dpFechaNacimiento.setValue(
                        seleccionado.getFechaNacimiento()
                                .toInstant()
                                .atZone(java.time.ZoneId.systemDefault())
                                .toLocalDate()
                );
            }

            txtDireccion.setText(seleccionado.getDireccion());
            txtTelefono.setText(seleccionado.getTelefono());

            cbEstado.setValue(seleccionado.getEstado());

        } else {
            mostrarAlerta("Seleccione un paciente.");
        }
    }

    private void limpiarCampos() {

        txtNombre.clear();
        txtApellido.clear();

        dpFechaNacimiento.setValue(null);

        txtDireccion.clear();
        txtTelefono.clear();

        cbEstado.setValue(null);
    }

    private void mostrarAlerta(String mensaje) {

        Alert alerta = new Alert(Alert.AlertType.WARNING);

        alerta.setTitle("Atención");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);

        alerta.showAndWait();
    }
}