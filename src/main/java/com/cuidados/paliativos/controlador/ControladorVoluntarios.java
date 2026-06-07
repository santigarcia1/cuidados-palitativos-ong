package com.cuidados.paliativos.controlador;

import com.cuidados.paliativos.modelo.Area;
import com.cuidados.paliativos.modelo.Usuario;
import com.cuidados.paliativos.modelo.Voluntario;
import com.cuidados.paliativos.seguridad.Permisos;
import com.cuidados.paliativos.seguridad.Sesion;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ControladorVoluntarios {

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtApellido;

    @FXML
    private TextField txtTelefono;

    @FXML
    private ComboBox<Area> cbArea;

    @FXML
    private ComboBox<Usuario> cbUsuario;

    @FXML
    private TableView<Voluntario> tablaVoluntarios;

    @FXML
    private TableColumn<Voluntario, Long> colId;

    @FXML
    private TableColumn<Voluntario, String> colNombre;

    @FXML
    private TableColumn<Voluntario, String> colApellido;

    @FXML
    private TableColumn<Voluntario, String> colArea;

    @FXML
    private TableColumn<Voluntario, String> colTelefono;

    @FXML
    private TableColumn<Voluntario, String> colUsuario;

    @FXML
    private Button btnNuevo;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnModificar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnConsultar;

    private final ObservableList<Voluntario> listaVoluntarios =
            FXCollections.observableArrayList();

    private final ObservableList<Area> listaAreas =
            FXCollections.observableArrayList();

    private final ObservableList<Usuario> listaUsuarios =
            FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        configurarPermisos();

        // AREAS

        Area a1 = new Area();
        a1.setId(1L);
        a1.setNombre("Acompañamiento");

        Area a2 = new Area();
        a2.setId(2L);
        a2.setNombre("Administración");

        Area a3 = new Area();
        a3.setId(3L);
        a3.setNombre("Eventos");

        listaAreas.addAll(a1, a2, a3);

        // USUARIOS

        Usuario u1 = new Usuario();
        u1.setId(1L);
        u1.setEmail("voluntario1@gmail.com");

        Usuario u2 = new Usuario();
        u2.setId(2L);
        u2.setEmail("voluntario2@gmail.com");

        Usuario u3 = new Usuario();
        u3.setId(3L);
        u3.setEmail("voluntario3@gmail.com");

        listaUsuarios.addAll(u1, u2, u3);

        cbArea.setItems(listaAreas);
        cbUsuario.setItems(listaUsuarios);

        // COMBO AREA

        cbArea.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Area item, boolean empty) {
                super.updateItem(item, empty);

                setText((empty || item == null)
                        ? null
                        : item.getNombre());
            }
        });

        cbArea.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Area item, boolean empty) {
                super.updateItem(item, empty);

                setText((empty || item == null)
                        ? null
                        : item.getNombre());
            }
        });

        // COMBO USUARIO

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

        // TABLA

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

        colArea.setCellValueFactory(c ->
                new SimpleStringProperty(
                        c.getValue().getArea() != null
                                ? c.getValue().getArea().getNombre()
                                : ""
                ));

        colTelefono.setCellValueFactory(c ->
                new SimpleStringProperty(
                        c.getValue().getTelefono()
                ));

        colUsuario.setCellValueFactory(c ->
                new SimpleStringProperty(
                        c.getValue().getUsuario() != null
                                ? c.getValue().getUsuario().getEmail()
                                : ""
                ));

        tablaVoluntarios.setItems(listaVoluntarios);
    }

    @FXML
    private void nuevoVoluntario() {
        if (!tienePermisoGestion()) {
            mostrarAlerta("No posee permisos para realizar esta acción.");
            return;
        }
        limpiarCampos();
    }

    @FXML
    private void guardarVoluntario() {
        if (!tienePermisoGestion()) {
            mostrarAlerta("No posee permisos para realizar esta acción.");
            return;
        }

        if (txtNombre.getText().isEmpty() ||
                txtApellido.getText().isEmpty() ||
                txtTelefono.getText().isEmpty() ||
                cbArea.getValue() == null ||
                cbUsuario.getValue() == null) {

            mostrarAlerta("Complete todos los campos.");
            return;
        }

        Voluntario nuevo = new Voluntario();

        nuevo.setId((long) (listaVoluntarios.size() + 1));
        nuevo.setNombre(txtNombre.getText());
        nuevo.setApellido(txtApellido.getText());
        nuevo.setTelefono(txtTelefono.getText());
        nuevo.setArea(cbArea.getValue());
        nuevo.setUsuario(cbUsuario.getValue());

        listaVoluntarios.add(nuevo);

        limpiarCampos();
    }

    @FXML
    private void modificarVoluntario() {
        if (!tienePermisoGestion()) {
            mostrarAlerta("No posee permisos para realizar esta acción.");
            return;
        }

        Voluntario seleccionado =
                tablaVoluntarios.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {

            seleccionado.setNombre(txtNombre.getText());
            seleccionado.setApellido(txtApellido.getText());
            seleccionado.setTelefono(txtTelefono.getText());
            seleccionado.setArea(cbArea.getValue());
            seleccionado.setUsuario(cbUsuario.getValue());

            tablaVoluntarios.refresh();

            limpiarCampos();

        } else {
            mostrarAlerta("Seleccione un voluntario.");
        }
    }

    @FXML
    private void eliminarVoluntario() {
        if (!tienePermisoGestion()) {
            mostrarAlerta("No posee permisos para realizar esta acción.");
            return;
        }

        Voluntario seleccionado =
                tablaVoluntarios.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {

            listaVoluntarios.remove(seleccionado);

        } else {
            mostrarAlerta("Seleccione un voluntario.");
        }
    }

    @FXML
    private void consultarVoluntario() {

        Voluntario seleccionado =
                tablaVoluntarios.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {

            txtNombre.setText(seleccionado.getNombre());
            txtApellido.setText(seleccionado.getApellido());
            txtTelefono.setText(seleccionado.getTelefono());

            cbArea.setValue(seleccionado.getArea());
            cbUsuario.setValue(seleccionado.getUsuario());

        } else {
            mostrarAlerta("Seleccione un voluntario.");
        }
    }

    private void limpiarCampos() {

        txtNombre.clear();
        txtApellido.clear();
        txtTelefono.clear();

        cbArea.setValue(null);
        cbUsuario.setValue(null);
    }

    private void mostrarAlerta(String mensaje) {

        Alert alerta = new Alert(Alert.AlertType.WARNING);

        alerta.setTitle("Atención");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);

        alerta.showAndWait();
    }

    private boolean tienePermisoGestion() {

        Usuario usuario = Sesion.getUsuarioLogueado();

        return Permisos.esAdministrador(usuario)
                || Permisos.esAdministrativo(usuario);
    }

    private void configurarPermisos() {
        Usuario usuario = Sesion.getUsuarioLogueado();

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

            cbArea.setDisable(true);
            cbUsuario.setDisable(true);
        }
    }
}