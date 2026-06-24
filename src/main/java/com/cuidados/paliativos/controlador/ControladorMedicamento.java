package com.cuidados.paliativos.controlador;

import com.cuidados.paliativos.dao.FrecuenciaDAO;
import com.cuidados.paliativos.dao.FrecuenciaDAOImpl;
import com.cuidados.paliativos.dao.MedicamentoDAO;
import com.cuidados.paliativos.dao.MedicamentoDAOImpl;
import com.cuidados.paliativos.modelo.Medicamento;
import com.cuidados.paliativos.modelo.Frecuencia;
import com.cuidados.paliativos.modelo.Usuario;
import com.cuidados.paliativos.seguridad.Permisos;
import com.cuidados.paliativos.seguridad.Sesion;
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

    @FXML
    private Button btnNuevo;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnModificar;

    @FXML
    private Button btnEliminar;

    private final ObservableList<Medicamento> listaMedicamentos = FXCollections.observableArrayList();
    private final ObservableList<Frecuencia> listaFrecuencias = FXCollections.observableArrayList();

    private final MedicamentoDAO medicamentoDAO = new MedicamentoDAOImpl();

    private final FrecuenciaDAO frecuenciaDAO = new FrecuenciaDAOImpl();

    @FXML
    private void initialize() {
        configurarPermisos();

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

        tablaMedicamentos.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, anterior, seleccionado) -> {
                    if (seleccionado != null) {
                        txtNombreMedicamento.setText(seleccionado.getNombre());
                        txtDosis.setText(seleccionado.getDosis());
                        cbFrecuencia.setValue(seleccionado.getFrecuencia());
                    }
                });

        cargarMedicamentos();
        cargarFrecuencias();
    }

    @FXML
    private void nuevoMedicamento() {
        limpiarCampos();
    }

    @FXML
    private void guardarMedicamento() {
        if (!tienePermisoEdicion()) {
            return;
        }

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

        medicamentoDAO.guardar(nuevo);
        cargarMedicamentos();
        limpiarCampos();
    }

    @FXML
    private void modificarMedicamento() {
        if (!tienePermisoEdicion()) {
            return;
        }

        Medicamento seleccionado = tablaMedicamentos.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            seleccionado.setNombre(txtNombreMedicamento.getText());
            seleccionado.setDosis(txtDosis.getText());
            seleccionado.setFrecuencia(cbFrecuencia.getValue());
            medicamentoDAO.modificar(seleccionado);
            cargarMedicamentos();
            limpiarCampos();
        } else {
            mostrarAlerta("Seleccione un medicamento para modificar.");
        }
    }

    @FXML
    private void eliminarMedicamento() {
        if (!tienePermisoEdicion()) {
            return;
        }

        Medicamento seleccionado = tablaMedicamentos.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            medicamentoDAO.eliminar(seleccionado.getId());
            cargarMedicamentos();
            limpiarCampos();
        } else {
            mostrarAlerta("Seleccione un medicamento para eliminar.");
        }
    }

    private void cargarMedicamentos() {
        listaMedicamentos.clear();
        listaMedicamentos.addAll(medicamentoDAO.listar());
        tablaMedicamentos.setItems(listaMedicamentos);
    }

    private void cargarFrecuencias() {
        listaFrecuencias.clear();
        listaFrecuencias.addAll(frecuenciaDAO.listar());
        cbFrecuencia.setItems(listaFrecuencias);
    }

    private void limpiarCampos() {
        txtNombreMedicamento.clear();
        txtDosis.clear();
        cbFrecuencia.setValue(null);
        tablaMedicamentos.getSelectionModel().clearSelection();
    }

    private void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Atención");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void configurarPermisos() {
        Usuario usuario = Sesion.getInstancia().getUsuarioLogueado();

        if (usuario == null) {
            return;
        }

        if (Permisos.esVoluntario(usuario) ||
                Permisos.esAdministrativo(usuario)) {

            btnNuevo.setDisable(true);
            btnGuardar.setDisable(true);
            btnModificar.setDisable(true);
            btnEliminar.setDisable(true);
        }
    }

    private boolean tienePermisoEdicion() {
        Usuario usuario = Sesion.getInstancia().getUsuarioLogueado();

        if (Permisos.esVoluntario(usuario) ||
                Permisos.esAdministrativo(usuario)) {

            mostrarAlerta(
                    "No posee permisos para acceder a esta funcionalidad."
            );

            return false;
        }

        return true;
    }
}
