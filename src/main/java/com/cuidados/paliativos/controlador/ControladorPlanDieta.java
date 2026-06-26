package com.cuidados.paliativos.controlador;

import com.cuidados.paliativos.dao.*;
import com.cuidados.paliativos.modelo.Dieta;
import com.cuidados.paliativos.modelo.PlanCuidado;
import com.cuidados.paliativos.modelo.TipoDieta;
import com.cuidados.paliativos.modelo.Usuario;
import com.cuidados.paliativos.seguridad.Permisos;
import com.cuidados.paliativos.seguridad.Sesion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;

public class ControladorPlanDieta {

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

    @FXML
    private Button btnNuevo;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnModificar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnDetalles;

    private PlanCuidado planSeleccionado;

    private final ObservableList<Dieta> listaDietas = FXCollections.observableArrayList();

    private final ObservableList<TipoDieta> listaTiposDieta = FXCollections.observableArrayList();

    private final DietaDAO dietaDAO = new DietaDAOImpl();

    private final TipoDietaDAO tipoDietaDAO = new TipoDietaDAOImpl();

    private final PlanDietaDAO planDietaDAO = new PlanDietaImpl();

    @FXML
    private void initialize() {
        configurarPermisos();

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

        tablaDietas.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, anterior, seleccionado) -> {
                    if (seleccionado != null) {
                        txtNombreDieta.setText(seleccionado.getNombre());
                        txtDescripcion.setText(seleccionado.getDescripcion());
                        cbTipoDieta.setValue(seleccionado.getTipoDieta());
                    }
                });
    }

    public void setPlanCuidado(PlanCuidado plan) {
        this.planSeleccionado = plan;
        cargarDietas();
        cargarTiposDieta();
    }

    @FXML
    private void nuevaDieta() {
        if (!tienePermisoEdicion()) {
            return;
        }
        limpiarCampos();
    }

    @FXML
    private void guardarDieta() {
        if (!tienePermisoEdicion()) {
            return;
        }

        if (txtNombreDieta.getText().isEmpty() || txtDescripcion.getText().isEmpty() || cbTipoDieta.getValue() == null) {
            mostrarAlerta("Complete todos los campos antes de guardar.");
            return;
        }

        Dieta nuevaDieta = new Dieta();
        nuevaDieta.setNombre(txtNombreDieta.getText());
        nuevaDieta.setDescripcion(txtDescripcion.getText());
        nuevaDieta.setTipoDieta(cbTipoDieta.getValue());

        Long idNueva = dietaDAO.guardar(nuevaDieta);
        planDietaDAO.guardar(this.planSeleccionado.getId(), idNueva);
        cargarDietas();
        limpiarCampos();
    }

    @FXML
    private void modificarDieta() {
        if (!tienePermisoEdicion()) {
            return;
        }

        Dieta seleccionada = tablaDietas.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            seleccionada.setNombre(txtNombreDieta.getText());
            seleccionada.setDescripcion(txtDescripcion.getText());
            seleccionada.setTipoDieta(cbTipoDieta.getValue());
            dietaDAO.modificar(seleccionada);
            cargarDietas();
            limpiarCampos();
        } else {
            mostrarAlerta("Seleccione una dieta para modificar.");
        }
    }

    @FXML
    private void eliminarDieta() {
        if (!tienePermisoEdicion()) {
            return;
        }

        Dieta seleccionada = tablaDietas.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            planDietaDAO.eliminar(this.planSeleccionado.getId(), seleccionada.getId());
            dietaDAO.eliminar(seleccionada.getId());
            cargarDietas();
            limpiarCampos();
        } else {
            mostrarAlerta("Seleccione una dieta para eliminar.");
        }
    }

    @FXML
    private void abrirDetalleDieta() {
        if (!tienePermisoEdicion()) {
            return;
        }

        Dieta seleccionada = tablaDietas.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarAlerta("Seleccione una dieta para gestionar sus horarios.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cuidados/paliativos/vista/detalle-dieta.fxml"));
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

    private void cargarDietas() {
        listaDietas.clear();
        listaDietas.addAll(dietaDAO.buscarPorIdDePlanDeCuidado(this.planSeleccionado.getId()));
        tablaDietas.setItems(listaDietas);
    }

    private void cargarTiposDieta() {
        listaTiposDieta.clear();
        listaTiposDieta.addAll(tipoDietaDAO.listar());
        cbTipoDieta.setItems(listaTiposDieta);
    }

    private void limpiarCampos() {
        txtNombreDieta.clear();
        txtDescripcion.clear();
        cbTipoDieta.setValue(null);
        tablaDietas.getSelectionModel().clearSelection();
    }

    private void mostrarAlerta(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Atención");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
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
            btnDetalles.setDisable(true);
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
