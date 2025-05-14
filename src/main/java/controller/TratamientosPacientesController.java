package controller;

import DAO.PacienteDAO;
import DAO.TratamientoDAO;
import DAO.TratamientoPacienteDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

@Override
public void initialize(URL location, ResourceBundle resources) {
    logger.info("Inicializando controlador TratamientosPacientesController...");

    // Configurar las columnas
    colPaciente.setCellValueFactory(cellData -> {
        int idPaciente = cellData.getValue().getIdPaciente();
        String nombrePaciente = PacienteDAO.getInstance().findNameById(idPaciente); // Método en PacienteDAO
        return new SimpleStringProperty(nombrePaciente != null ? nombrePaciente : "Desconocido");
    });

    colTratamiento.setCellValueFactory(cellData -> {
        int idTratamiento = cellData.getValue().getIdTratamiento();
        String descripcionTratamiento = TratamientoDAO.getInstance().findDescripcionById(idTratamiento); // Método en TratamientoDAO
        return new SimpleStringProperty(descripcionTratamiento != null ? descripcionTratamiento : "Desconocido");
    });

    colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaTratamiento"));
    colDetalles.setCellValueFactory(new PropertyValueFactory<>("detalles"));

    // Establecer anchos preferidos para las columnas
    colPaciente.setPrefWidth(150);
    colTratamiento.setPrefWidth(500);
    colFecha.setPrefWidth(120);
    colDetalles.setPrefWidth(700);

    // Cargar los datos en la tabla
    cargarTratamientosPacientes();
}

    private void cargarTratamientosPacientes() {
        logger.info("Cargando tratamientos de pacientes...");
        try {
            List<TratamientoPaciente> tratamientos = TratamientoPacienteDAO.getInstance().findAll();
            logger.info("Tratamientos obtenidos: " + tratamientos);
            tratamientosPacientesList = FXCollections.observableArrayList(tratamientos);
            tratamientosPacientesTable.setItems(tratamientosPacientesList);
        } catch (Exception e) {
            logger.severe("Error al cargar tratamientos de pacientes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void volver(javafx.event.ActionEvent event) {
        logger.info("Intentando volver al menú principal...");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main.fxml"));
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
}