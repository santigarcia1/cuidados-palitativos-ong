package com.cuidados.paliativos.controlador;

import com.cuidados.paliativos.modelo.Especialidad;
import com.cuidados.paliativos.modelo.Profesional;
import com.cuidados.paliativos.modelo.Usuario;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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

    private final ObservableList<Profesional> listaProfesionales =
            FXCollections.observableArrayList();

    private final ObservableList<Especialidad> listaEspecialidades =
            FXCollections.observableArrayList();

    private final ObservableList<Usuario> listaUsuarios =
            FXCollections.observableArrayList();

    @FXML
    private void initialize() {

        // DATOS DE EJEMPLO

        listaEspecialidades.addAll(
                new Especialidad(1L, "Cardiología", "Estudios del corazon."),
                new Especialidad(2L, "Oncología", "Oncologia"),
                new Especialidad(3L, "Clínica Médica", "Medicos clinicos")
        );

        Usuario u1 = new Usuario();
        u1.setId(1L);
        u1.setEmail("admin@gmail.com");

        Usuario u2 = new Usuario();
        u2.setId(2L);
        u2.setEmail("doctor@gmail.com");

        Usuario u3 = new Usuario();
        u3.setId(3L);
        u3.setEmail("medico@gmail.com");

        listaUsuarios.addAll(u1, u2, u3);

        cbEspecialidad.setItems(listaEspecialidades);
        cbUsuario.setItems(listaUsuarios);

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

        tablaProfesionales.setItems(listaProfesionales);
    }

    @FXML
    private void nuevoProfesional() {
        limpiarCampos();
    }

    @FXML
    private void guardarProfesional() {

        if (txtNombre.getText().isEmpty() ||
                txtApellido.getText().isEmpty() ||
                txtTelefono.getText().isEmpty() ||
                cbEspecialidad.getValue() == null ||
                cbUsuario.getValue() == null) {

            mostrarAlerta("Complete todos los campos.");
            return;
        }

        Profesional nuevo = new Profesional();

        nuevo.setId((long) (listaProfesionales.size() + 1));
        nuevo.setNombre(txtNombre.getText());
        nuevo.setApellido(txtApellido.getText());
        nuevo.setTelefono(txtTelefono.getText());
        nuevo.setEspecialidad(cbEspecialidad.getValue());
        nuevo.setUsuario(cbUsuario.getValue());

        listaProfesionales.add(nuevo);

        limpiarCampos();
    }

    @FXML
    private void modificarProfesional() {

        Profesional seleccionado =
                tablaProfesionales.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {

            seleccionado.setNombre(txtNombre.getText());
            seleccionado.setApellido(txtApellido.getText());
            seleccionado.setTelefono(txtTelefono.getText());
            seleccionado.setEspecialidad(cbEspecialidad.getValue());
            seleccionado.setUsuario(cbUsuario.getValue());

            tablaProfesionales.refresh();

            limpiarCampos();

        } else {
            mostrarAlerta("Seleccione un profesional.");
        }
    }

    @FXML
    private void eliminarProfesional() {

        Profesional seleccionado =
                tablaProfesionales.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {

            listaProfesionales.remove(seleccionado);

        } else {
            mostrarAlerta("Seleccione un profesional.");
        }
    }

    @FXML
    private void consultarProfesional() {

        Profesional seleccionado =
                tablaProfesionales.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {

            txtNombre.setText(seleccionado.getNombre());
            txtApellido.setText(seleccionado.getApellido());
            txtTelefono.setText(seleccionado.getTelefono());

            cbEspecialidad.setValue(seleccionado.getEspecialidad());
            cbUsuario.setValue(seleccionado.getUsuario());

        } else {
            mostrarAlerta("Seleccione un profesional.");
        }
    }

    private void limpiarCampos() {

        txtNombre.clear();
        txtApellido.clear();
        txtTelefono.clear();

        cbEspecialidad.setValue(null);
        cbUsuario.setValue(null);
    }

    private void mostrarAlerta(String mensaje) {

        Alert alerta = new Alert(Alert.AlertType.WARNING);

        alerta.setTitle("Atención");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);

        alerta.showAndWait();
    }
}