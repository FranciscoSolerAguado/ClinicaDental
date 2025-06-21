package controller;

import DAO.TratamientoPacienteDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.TratamientoPaciente;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class TratamientosPacientesController implements Initializable {

    private static final Logger logger = Logger.getLogger(TratamientosPacientesController.class.getName());

    @FXML
    private TableView<TratamientoPaciente> tratamientosPacientesTable;

    @FXML
    private TableColumn<TratamientoPaciente, String> colPaciente;

    @FXML
    private TableColumn<TratamientoPaciente, String> colTratamiento;

    @FXML
    private TableColumn<TratamientoPaciente, String> colFecha;

    @FXML
    private TableColumn<TratamientoPaciente, String> colDetalles;

    private ObservableList<TratamientoPaciente> tratamientosPacientesList;

    /**
     * Método que se ejecuta al inicializar la vista.
     * Configura las columnas de la tabla y carga los datos.
     */
    private TratamientoPacienteDAO tratamientoPacienteDAO;

@Override
public void initialize(URL location, ResourceBundle resources) {
    logger.info("Inicializando controlador TratamientoPacienteController...");

    // Inicializar el campo tratamientoPacienteDAO
    this.tratamientoPacienteDAO = TratamientoPacienteDAO.getInstance();
    if (this.tratamientoPacienteDAO == null) {
        throw new IllegalStateException("tratamientoPacienteDAO no se inicializó correctamente.");
    }

    // Configurar las columnas
    colPaciente.setCellValueFactory(cellData -> {
        String nombrePaciente = cellData.getValue().getPaciente() != null
                ? cellData.getValue().getPaciente().getNombre()
                : "Desconocido";
        return new SimpleStringProperty(nombrePaciente);
        /**
         * Esta línea crea una nueva propiedad observable de tipo String para JavaFX (SimpleStringProperty).
         * El valor de la propiedad será el nombre del paciente si no es null; si es null, mostrará el texto "Desconocido".
         * Esto permite que la tabla muestre el nombre del paciente asociado al tratamiento.
         */
    });

    colTratamiento.setCellValueFactory(cellData -> {
        String descripcionTratamiento = cellData.getValue().getTratamiento() != null
                ? cellData.getValue().getTratamiento().getDescripcion()
                : "Desconocido";
        return new SimpleStringProperty(descripcionTratamiento);
    });

    colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaTratamiento"));
    colDetalles.setCellValueFactory(new PropertyValueFactory<>("detalles"));

    colTratamiento.setPrefWidth(400);
    colDetalles.setPrefWidth(810);

    // Cargar los datos en la tabla
    cargarTratamientosPacientes();
}


    /**
     * Carga los tratamientos de pacientes desde la base de datos y los muestra en la tabla.
     */
public void cargarTratamientosPacientes() {
    logger.info("Cargando tratamientos de pacientes...");
    if (this.tratamientoPacienteDAO == null) {
        logger.severe("tratamientoPacienteDAO es null. No se puede cargar tratamientos.");
        return;
    }

    try {
        List<TratamientoPaciente> tratamientos = this.tratamientoPacienteDAO.findAll();
        logger.info("Tratamientos obtenidos: " + tratamientos);
        tratamientosPacientesList = FXCollections.observableArrayList(tratamientos);
        tratamientosPacientesTable.setItems(tratamientosPacientesList);
    } catch (Exception e) {
        logger.severe("Error al cargar tratamientos de pacientes: " + e.getMessage());
        e.printStackTrace();
    }
}

    /**
     * Método para manejar el evento de volver al menú principal.
     *
     * @param event Evento de acción.
     */
    @FXML
    private void volver(javafx.event.ActionEvent event) {
        logger.info("Intentando volver al menú principal...");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            logger.info("Regresado al menú principal con éxito.");
        } catch (IOException e) {
            logger.severe("Error al volver al menú principal: " + e.getMessage());
            e.printStackTrace();
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("No se pudo volver al menú principal");
            alerta.setContentText("Ocurrió un error al intentar cargar la vista principal.");
            alerta.showAndWait();
        }
    }

    /**
     * Método para manejar el evento de abrir el formulario de añadir tratamiento.
     *
     * @param event Evento de acción.
     */
    @FXML
    private void abrirFormularioTratamientoPaciente(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/tratamientosPacientesForm.fxml"));
            Parent root = loader.load();

            // Obtener el controlador del formulario
            TratamientosPacientesFormController formController = loader.getController();
            formController.setTratatamientosPacientesController(this); // Pasar referencia del controlador actual

            Stage stage = new Stage();
            stage.setTitle("Añadir TratamientoPaciente");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            logger.severe("Error al abrir el formulario de añadir TratamientoPaciente: " + e.getMessage());
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("No se pudo abrir el formulario");
            alerta.setContentText("Ocurrió un error al intentar abrir el formulario de añadir TratamientoPaciente.");
            alerta.showAndWait();
        }
    }

    /**
     * Método para manejar el evento de eliminar un tratamiento de paciente.
     */
@FXML
private void eliminarTratamientoPaciente() {
    TratamientoPaciente seleccionado = tratamientosPacientesTable.getSelectionModel().getSelectedItem();

    if (seleccionado == null) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Advertencia");
        alerta.setHeaderText("Ningún tratamiento seleccionado");
        alerta.setContentText("Por favor, selecciona un tratamiento de la lista.");
        alerta.showAndWait();
        return;
    }

    try {
        // Eliminar de la base de datos
        TratamientoPacienteDAO.getInstance().delete(
                seleccionado.getPaciente().getIdPaciente(),
                seleccionado.getTratamiento().getIdTratamiento(),
                java.sql.Date.valueOf(seleccionado.getFechaTratamiento())
        );

        // Eliminar de la lista observable
        tratamientosPacientesList.remove(seleccionado);

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Éxito");
        alerta.setHeaderText("Tratamiento eliminado");
        alerta.setContentText("El tratamiento se ha eliminado correctamente.");
        alerta.showAndWait();
    } catch (Exception e) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setHeaderText("No se pudo eliminar el tratamiento");
        alerta.setContentText("Ocurrió un error al intentar eliminar el tratamiento.");
        alerta.showAndWait();
    }
}

    /**
     * Método para manejar el evento de editar un tratamiento de paciente.
     */
    @FXML
    private void editarTratamientoPaciente() {
        TratamientoPaciente seleccionado = tratamientosPacientesTable.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Advertencia");
            alerta.setHeaderText("Ningún tratamiento seleccionado");
            alerta.setContentText("Por favor, selecciona un tratamiento de la lista.");
            alerta.showAndWait();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/tratamientosPacientesEditForm.fxml"));
            Parent root = loader.load();

            TratamientosPacientesFormController controller = loader.getController();
            controller.setTratatamientosPacientesController(this); // Pasar referencia del controlador principal
            controller.setTratamientoPaciente(seleccionado); // Pasar el tratamiento seleccionado

            Stage stage = new Stage();
            stage.setTitle("Editar Tratamiento de Paciente");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("No se pudo abrir el formulario");
            alerta.setContentText("Ocurrió un error al intentar abrir el formulario de edición.");
            alerta.showAndWait();
        }
    }
}