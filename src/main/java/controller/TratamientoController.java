package controller;

import DAO.TratamientoDAO;
import exceptions.TratamientoNoEncontradoException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.Tratamiento;
import model.Tratamiento;

import java.io.IOException;

public class TratamientoController {
    @FXML
    private ListView<String> tratamientoListView;
    private final TratamientoDAO tratamientoDAO = TratamientoDAO.getInstance();
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(TratamientoController.class.getName());

    @FXML
    public void initialize() {
        // Cargar los nombres de los tratamientos en el ListView
        tratamientoDAO.findAll().forEach(tratamiento -> {
            if (tratamiento instanceof Tratamiento) {
                tratamientoListView.getItems().add(((Tratamiento) tratamiento).getDescripcion());
            }
        });
    }


    @FXML
    private void volverAMain(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/main.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaximized(true); // Maximizar la ventana con bordes
        stage.show();
    }

    @FXML
    public void cargarTratamientos() {
        try {
            tratamientoListView.getItems().clear();
            tratamientoDAO.findAll().forEach(tratamiento -> {
                if (tratamiento instanceof Tratamiento) {
                    tratamientoListView.getItems().add(((Tratamiento) tratamiento).getDescripcion());
                }
            });
            logger.info("Lista de tratamientos cargada correctamente.");
        } catch (Exception e) {
            logger.severe("Error al cargar la lista de tratamientos: " + e.getMessage());
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Error al cargar la lista de tratamientos");
            alerta.setContentText("No se pudo cargar la lista de tratamientos. Intenta nuevamente.");
            alerta.showAndWait();
        }
    }

    @FXML
    private void mostrarMas() {
        logger.info("Intentando mostrar información del tratamiento seleccionado...");

        String descripcionSeleccionada = tratamientoListView.getSelectionModel().getSelectedItem();

        if (descripcionSeleccionada == null) {
            logger.warning("No se seleccionó ningún tratamiento.");
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Advertencia");
            alerta.setHeaderText("Ningún tratamiento seleccionado");
            alerta.setContentText("Por favor, selecciona un tratamiento de la lista.");
            alerta.showAndWait();
            return;
        }

        try {
            logger.info("Buscando información del tratamiento: " + descripcionSeleccionada);
            logger.info("Tratamiento mostrado: " + descripcionSeleccionada);
            Tratamiento tratamiento = tratamientoDAO.findByDescripcionEager(descripcionSeleccionada);
            if (tratamiento == null) {
                throw new TratamientoNoEncontradoException("No se encontró el tratamiento con la descripción: " + descripcionSeleccionada);
            }

            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Información del Tratamiento");
            alerta.setHeaderText("Detalles del tratamiento seleccionado");
            alerta.setContentText(
                    "idTratamiento: " + tratamiento.getIdTratamiento() + "\n" +
                            "Descripción: " + tratamiento.getDescripcion() + "\n" +
                            "Precio: " + tratamiento.getPrecio() + "\n" +
                            "Dentista especialista: " + tratamiento.getDentista().getNombre() + "\n"
            );
            alerta.showAndWait();
        } catch (TratamientoNoEncontradoException e) {
            logger.warning(e.getMessage());
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Tratamiento no encontrado");
            alerta.setContentText(e.getMessage());
            alerta.showAndWait();
        } catch (Exception e) {
            logger.severe("Error al buscar el tratamiento: " + e.getMessage());
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Error al buscar el tratamiento");
            alerta.setContentText("Ocurrió un error al buscar el tratamiento seleccionado.");
            alerta.showAndWait();
        }
    }

    @FXML
    private void abrirFormularioTratamiento() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tratamientoForm.fxml"));
            Parent root = loader.load();

            // Obtener el controlador del formulario
            TratamientoFormController formController = loader.getController();
            formController.setTratamientoController(this); // Pasar referencia del controlador actual

            Stage stage = new Stage();
            stage.setTitle("Añadir Tratamiento");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            logger.severe("Error al abrir el formulario de añadir tratamiento: " + e.getMessage());
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("No se pudo abrir el formulario");
            alerta.setContentText("Ocurrió un error al intentar abrir el formulario de añadir tratamiento.");
            alerta.showAndWait();
        }
    }
}
