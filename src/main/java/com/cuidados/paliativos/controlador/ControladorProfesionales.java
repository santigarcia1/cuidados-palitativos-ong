package com.cuidados.paliativos.controlador;

import com.cuidados.paliativos.dao.*;
import com.cuidados.paliativos.modelo.Especialidad;
import com.cuidados.paliativos.modelo.Profesional;
import com.cuidados.paliativos.modelo.Usuario;
import com.cuidados.paliativos.seguridad.Permisos;
import com.cuidados.paliativos.seguridad.Sesion;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Optional;

public class ControladorProfesionales {

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtApellido;

    @FXML
    private TextField txtTelefono;

    @FXML
    private ComboBox<Especialidad> cbEspecialidad;

    @FXML
    private ComboBox<Usuario> cbUsuario;

    @FXML
    private TableView<Profesional> tablaProfesionales;

    @FXML
    private TableColumn<Profesional, Long> colId;

    @FXML
    private TableColumn<Profesional, String> colNombre;

    @FXML
    private TableColumn<Profesional, String> colApellido;

    @FXML
    private TableColumn<Profesional, String> colEspecialidad;

    @FXML
    private TableColumn<Profesional, String> colTelefono;

    @FXML
    private TableColumn<Profesional, String> colUsuario;

    @FXML
    private Button btnNuevo;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnModificar;

    @FXML
    private Button btnEliminar;

    private final ObservableList<Profesional> listaProfesionales =
            FXCollections.observableArrayList();

    private final ObservableList<Especialidad> listaEspecialidades =
            FXCollections.observableArrayList();

    private final ObservableList<Usuario> listaUsuarios =
            FXCollections.observableArrayList();

    private final ProfesionalDAO profesionalDAO = new ProfesionalDAOImpl();

    private final UsuarioDAO usuarioDAO = new UsuarioDAOImpl();

    private final EspecialidadDAO especialidadDAO = new EspecialidadDAOImpl();

    @FXML
    private void initialize() {
        configurarPermisos();

        // MOSTRAR NOMBRE EN COMBO ESPECIALIDAD
        cbEspecialidad.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Especialidad item, boolean empty) {
                super.updateItem(item, empty);
                setText((empty || item == null)
                        ? null
                        : item.getNombre());
            }
        });

        cbEspecialidad.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Especialidad item, boolean empty) {
                super.updateItem(item, empty);
                setText((empty || item == null)
                        ? null
                        : item.getNombre());
            }
        });

        // MOSTRAR USERNAME EN COMBO USUARIO
        cbUsuario.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Usuario item, boolean empty) {
                super.updateItem(item, empty);
                setText((empty || item == null)
                        ? null
                        : item.getEmail());
            }
        });

        cbUsuario.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Usuario item, boolean empty) {
                super.updateItem(item, empty);
                setText((empty || item == null)
                        ? null
                        : item.getEmail());
            }
        });

        // COLUMNAS TABLA
        colId.setCellValueFactory(c ->
                new javafx.beans.property.SimpleLongProperty(
                        c.getValue().getId()
                ).asObject());

        colNombre.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getNombre()
                ));

        colApellido.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getApellido()
                ));

        colEspecialidad.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getEspecialidad() != null
                                ? c.getValue().getEspecialidad().getNombre()
                                : ""
                ));

        colTelefono.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getTelefono()
                ));

        colUsuario.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getUsuario() != null
                                ? c.getValue().getUsuario().getEmail()
                                : ""
                ));

        tablaProfesionales.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, anterior, seleccionado) -> {
                    if (seleccionado != null) {
                        txtNombre.setText(seleccionado.getNombre());
                        txtApellido.setText(seleccionado.getApellido());
                        txtTelefono.setText(seleccionado.getTelefono());
                        cbEspecialidad.setValue(seleccionado.getEspecialidad());
                        cbUsuario.setValue(seleccionado.getUsuario());
                        btnGuardar.setDisable(true);
                    }
                });

        cargarProfesionales();
        cargarUsuarios();
        cargarEspecialidades();
    }

    @FXML
    private void nuevoProfesional() {
        if (!tienePermisoGestion()) {
            mostrarAlerta("No posee permisos para realizar esta acción.");
            return;
        }
        limpiarCampos();
    }

    @FXML
    private void guardarProfesional() {
        if (!tienePermisoGestion()) {
            mostrarAlerta("No posee permisos para realizar esta acción.");
            return;
        }

        if (txtNombre.getText().isEmpty() ||
                txtApellido.getText().isEmpty() ||
                txtTelefono.getText().isEmpty() ||
                cbEspecialidad.getValue() == null ||
                cbUsuario.getValue() == null) {

            mostrarAlerta("Complete todos los campos.");
            return;
        }

        if (!confirmarAccion("¿Está seguro de guardar el profesional?")) {
            return;
        }

        Profesional nuevo = new Profesional();

        nuevo.setId((long) (listaProfesionales.size() + 1));
        nuevo.setNombre(txtNombre.getText());
        nuevo.setApellido(txtApellido.getText());
        nuevo.setTelefono(txtTelefono.getText());
        nuevo.setEspecialidad(cbEspecialidad.getValue());
        nuevo.setUsuario(cbUsuario.getValue());

        profesionalDAO.guardar(nuevo);

        cargarProfesionales();
        limpiarCampos();
    }

    @FXML
    private void modificarProfesional() {
        if (!tienePermisoGestion()) {
            mostrarAlerta("No posee permisos para realizar esta acción.");
            return;
        }

        Profesional seleccionado =
                tablaProfesionales.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {
            if (!confirmarAccion("¿Está seguro de modificar el profesional?")) {
                return;
            }
            seleccionado.setNombre(txtNombre.getText());
            seleccionado.setApellido(txtApellido.getText());
            seleccionado.setTelefono(txtTelefono.getText());
            seleccionado.setEspecialidad(cbEspecialidad.getValue());
            seleccionado.setUsuario(cbUsuario.getValue());

            profesionalDAO.modificar(seleccionado);

            cargarProfesionales();

            limpiarCampos();

        } else {
            mostrarAlerta("Seleccione un profesional.");
        }
    }

    @FXML
    private void eliminarProfesional() {
        if (!tienePermisoGestion()) {
            mostrarAlerta("No posee permisos para realizar esta acción.");
            return;
        }

        Profesional seleccionado =
                tablaProfesionales.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {
            if (!confirmarAccion("¿Está seguro de eliminar el profesional?")) {
                return;
            }
            profesionalDAO.eliminar(seleccionado.getId());
            cargarProfesionales();
            limpiarCampos();

        } else {
            mostrarAlerta("Seleccione un profesional.");
        }
    }

    private void cargarProfesionales() {
        listaProfesionales.clear();
        listaProfesionales.addAll(profesionalDAO.listar());
        tablaProfesionales.setItems(listaProfesionales);
    }

    private void cargarUsuarios() {
        listaUsuarios.clear();
        listaUsuarios.addAll(usuarioDAO.listar());
        cbUsuario.setItems(listaUsuarios);
    }

    private void cargarEspecialidades() {
        listaEspecialidades.clear();
        listaEspecialidades.addAll(especialidadDAO.listar());
        cbEspecialidad.setItems(listaEspecialidades);
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtApellido.clear();
        txtTelefono.clear();

        cbEspecialidad.setValue(null);
        cbUsuario.setValue(null);

        btnGuardar.setDisable(false);

        tablaProfesionales.getSelectionModel().clearSelection();
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

    private boolean tienePermisoGestion() {

        Usuario usuario = Sesion.getInstancia().getUsuarioLogueado();

        return Permisos.esAdministrador(usuario)
                || Permisos.esAdministrativo(usuario);
    }

    private void configurarPermisos() {

        Usuario usuario = Sesion.getInstancia().getUsuarioLogueado();

        if (usuario == null) {
            return;
        }

        if (!tienePermisoGestion()) {

            btnNuevo.setDisable(true);
            btnGuardar.setDisable(true);
            btnModificar.setDisable(true);
            btnEliminar.setDisable(true);

            txtNombre.setEditable(false);
            txtApellido.setEditable(false);
            txtTelefono.setEditable(false);

            cbEspecialidad.setDisable(true);
            cbUsuario.setDisable(true);
        }
    }
}