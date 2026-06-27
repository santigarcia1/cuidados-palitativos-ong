package com.cuidados.paliativos.controlador;

import com.cuidados.paliativos.dao.EstadoUsuarioDAO;
import com.cuidados.paliativos.dao.EstadoUsuarioDAOImpl;
import com.cuidados.paliativos.dao.PacienteDAO;
import com.cuidados.paliativos.dao.PacienteDAOImpl;
import com.cuidados.paliativos.modelo.EstadoUsuario;
import com.cuidados.paliativos.modelo.Paciente;
import com.cuidados.paliativos.modelo.Usuario;
import com.cuidados.paliativos.seguridad.Permisos;
import com.cuidados.paliativos.seguridad.Sesion;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.Optional;

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

    @FXML
    private Button btnNuevo;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnModificar;

    @FXML
    private Button btnEliminar;

    private final ObservableList<Paciente> listaPacientes =
            FXCollections.observableArrayList();

    private final ObservableList<EstadoUsuario> listaEstados =
            FXCollections.observableArrayList();

    private final PacienteDAO pacienteDAO =
            new PacienteDAOImpl();

    private final EstadoUsuarioDAO estadoDAO =
            new EstadoUsuarioDAOImpl();

    @FXML
    private void initialize() {
        configurarPermisos();

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
                        c.getValue().getEstado().getNombre()
                ));

        cbEstado.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(EstadoUsuario item, boolean empty) {
                super.updateItem(item, empty);

                setText(
                        empty || item == null
                                ? null
                                : item.getNombre()
                );
            }
        });

        cbEstado.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(EstadoUsuario item, boolean empty) {
                super.updateItem(item, empty);

                setText(
                        empty || item == null
                                ? null
                                : item.getNombre()
                );
            }
        });

        tablaPacientes.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, anterior, seleccionado) -> {
                    if (seleccionado != null) {

                        txtNombre.setText(seleccionado.getNombre());
                        txtApellido.setText(seleccionado.getApellido());
                        txtDireccion.setText(seleccionado.getDireccion());
                        txtTelefono.setText(seleccionado.getTelefono());
                        cbEstado.setValue(seleccionado.getEstado());
                        btnGuardar.setDisable(true);
                        if (seleccionado.getFechaNacimiento() != null) {
                            java.sql.Date fecha =
                                    (java.sql.Date) seleccionado.getFechaNacimiento();

                            dpFechaNacimiento.setValue(
                                    fecha.toLocalDate()
                            );
                        }
                    }
                });

        cargarPacientes();
        cargarEstados();
    }

    @FXML
    private void nuevoPaciente() {
        if (!puedeModificar()) {
            mostrarAlerta("No posee permisos para realizar esta acción.");
            return;
        }
        limpiarCampos();
    }

    @FXML
    private void guardarPaciente() {
        if (!puedeModificar()) {
            mostrarAlerta("No posee permisos para realizar esta acción.");
            return;
        }

        if (txtNombre.getText().isEmpty() ||
                txtApellido.getText().isEmpty() ||
                dpFechaNacimiento.getValue() == null ||
                txtDireccion.getText().isEmpty() ||
                txtTelefono.getText().isEmpty() ||
                cbEstado.getValue() == null) {

            mostrarAlerta("Complete todos los campos.");
            return;
        }

        if (!confirmarAccion("¿Está seguro de guardar el paciente?")) {
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

        pacienteDAO.guardar(nuevo);

        cargarPacientes();
        limpiarCampos();
    }

    @FXML
    private void modificarPaciente() {
        if (!puedeModificar()) {
            mostrarAlerta("No posee permisos para realizar esta acción.");
            return;
        }

        Paciente seleccionado =
                tablaPacientes.getSelectionModel().getSelectedItem();

        if (seleccionado.getEstado().getId().equals(2L)) {
            mostrarAlerta("El paciente ya no se encuentra en la ONG.");
            return;
        }

        if (seleccionado != null) {
            if (!confirmarAccion("¿Está seguro de modificar el paciente?")) {
                return;
            }

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

            pacienteDAO.modificar(seleccionado);
            cargarPacientes();

            limpiarCampos();

        } else {
            mostrarAlerta("Seleccione un paciente.");
        }
    }

    @FXML
    private void eliminarPaciente() {
        if (!puedeModificar()) {
            mostrarAlerta("No posee permisos para realizar esta acción.");
            return;
        }

        Paciente seleccionado =
                tablaPacientes.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {
            if (!confirmarAccion("¿Está seguro de eliminar el paciente?")) {
                return;
            }
            pacienteDAO.eliminar(seleccionado.getId());
            cargarPacientes();
            limpiarCampos();
        } else {
            mostrarAlerta("Seleccione un paciente.");
        }
    }

    private void cargarPacientes() {
        listaPacientes.clear();
        listaPacientes.addAll(pacienteDAO.listar());
        tablaPacientes.setItems(listaPacientes);
    }

    private void cargarEstados() {
        listaEstados.clear();
        listaEstados.addAll(estadoDAO.listarTodos());
        cbEstado.setItems(listaEstados);
    }

    private void limpiarCampos() {

        txtNombre.clear();
        txtApellido.clear();

        dpFechaNacimiento.setValue(null);

        txtDireccion.clear();
        txtTelefono.clear();

        cbEstado.setValue(null);

        btnGuardar.setDisable(false);

        tablaPacientes.getSelectionModel().clearSelection();
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

    private void configurarPermisos() {

        Usuario usuario = Sesion.getInstancia().getUsuarioLogueado();

        if (usuario == null) {
            return;
        }

        /*
         * PROFESIONAL Y VOLUNTARIO
         * Solo consulta
         */
        if (Permisos.esProfesional(usuario)
                || Permisos.esVoluntario(usuario)) {

            btnNuevo.setDisable(true);
            btnGuardar.setDisable(true);
            btnModificar.setDisable(true);
            btnEliminar.setDisable(true);

            txtNombre.setEditable(false);
            txtApellido.setEditable(false);
            txtDireccion.setEditable(false);
            txtTelefono.setEditable(false);

            dpFechaNacimiento.setDisable(true);
            cbEstado.setDisable(true);
        }
    }

    private boolean puedeModificar() {

        Usuario usuario = Sesion.getInstancia().getUsuarioLogueado();

        return Permisos.esAdministrador(usuario)
                || Permisos.esAdministrativo(usuario);
    }
}