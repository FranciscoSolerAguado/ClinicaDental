package controller;

import DAO.DentistaDAO;
import exceptions.DentistaNoEncontradoException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.Dentista;

import java.io.IOException;
import java.util.logging.Logger;

public class DentistaController {

    @FXML
    private ListView<String> dentistaListView;

    private final DentistaDAO dentistaDAO = DentistaDAO.getInstance();
    private static final Logger logger = Logger.getLogger(DentistaController.class.getName());

    /**
     * Método que se ejecuta al inicializar la vista de Dentistas.
     * Carga la lista de dentistas desde la base de datos y la muestra en la interfaz.
     */
    @FXML
    public void initialize() {
        logger.info("Inicializando la vista de Dentistas...");
        cargarDentistas();
    }

    /**
     * Método para cargar la lista de dentistas desde la base de datos.
     * Limpia la lista actual y agrega los nombres de los dentistas obtenidos.
     */
    @FXML
    public void cargarDentistas() {
        try {
            dentistaListView.getItems().clear();
            dentistaDAO.findAll().forEach(dentista -> {
                if (dentista instanceof Dentista) {
                    dentistaListView.getItems().add(((Dentista) dentista).getNombre());
                }
            });
            logger.info("Lista de dentistas cargada correctamente.");
        } catch (Exception e) {
            logger.severe("Error al cargar la lista de dentistas: " + e.getMessage());
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Error al cargar la lista de dentistas");
            alerta.setContentText("No se pudo cargar la lista de dentistas. Intenta nuevamente.");
            alerta.showAndWait();
        }
    }

    /**
     * Método para volver a la vista principal de la aplicación.
     */
    @FXML
    private void volverAMain() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main.fxml"));
            Parent root = loader.load();
            Scene scene = dentistaListView.getScene();
            scene.setRoot(root);
        } catch (IOException e) {
            logger.severe("Error al cargar la vista principal: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Método para mostrar todos los datos sobre el dentista seleccionado.
     */
    @FXML
    private void mostrarMas() {
        logger.info("Intentando mostrar información del dentista seleccionado...");
        String nombreSeleccionado = dentistaListView.getSelectionModel().getSelectedItem();

        if (nombreSeleccionado == null) {
            logger.warning("No se seleccionó ningún dentista.");
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Advertencia");
            alerta.setHeaderText("Ningún dentista seleccionado");
            alerta.setContentText("Por favor, selecciona un dentista de la lista.");
            alerta.showAndWait();
            return;
        }

        try {
            logger.info("Buscando información del dentista: " + nombreSeleccionado);
            Dentista dentista = dentistaDAO.findByNameEager(nombreSeleccionado);
            if (dentista == null) {
                throw new DentistaNoEncontradoException("No se encontró el dentista con el nombre: " + nombreSeleccionado);
            }

            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Información del Dentista");
            alerta.setHeaderText("Detalles del dentista seleccionado");
            alerta.setContentText(
                    "Nombre: " + dentista.getNombre() + "\n" +
                    "DNI: " + dentista.getDni() + "\n" +
                    "Teléfono: " + dentista.getTelefono() + "\n" +
                    "Número Colegiado: " + dentista.getnColegiado() + "\n" +
                    "Especialidad: " + dentista.getEspecialidad() + "\n" +
                    "Fecha de Nacimiento: " + dentista.getFechaNacimiento() + "\n" +
                    "Edad: " + dentista.getEdad()
            );
            alerta.showAndWait();
        } catch (DentistaNoEncontradoException e) {
            logger.warning(e.getMessage());
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Dentista no encontrado");
            alerta.setContentText(e.getMessage());
            alerta.showAndWait();
        } catch (Exception e) {
            logger.severe("Error al buscar el dentista: " + e.getMessage());
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Error al buscar el dentista");
            alerta.setContentText("Ocurrió un error al buscar el dentista seleccionado.");
            alerta.showAndWait();
        }
    }

    @FXML
    private void abrirFormularioDentista() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dentistaForm.fxml"));
            Parent root = loader.load();

            // Obtener el controlador del formulario
            DentistaFormController formController = loader.getController();
            formController.setDentistaController(this); // Pasar referencia del controlador actual

            Stage stage = new Stage();
            stage.setTitle("Añadir Dentista");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            logger.severe("Error al abrir el formulario de añadir dentista: " + e.getMessage());
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("No se pudo abrir el formulario");
            alerta.setContentText("Ocurrió un error al intentar abrir el formulario de añadir dentista.");
            alerta.showAndWait();
        }
    }

    @FXML
    private void eliminarDentista() {
        String nombreSeleccionado = dentistaListView.getSelectionModel().getSelectedItem();

        if (nombreSeleccionado == null) {
            logger.warning("No se seleccionó ningún dentista.");
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Advertencia");
            alerta.setHeaderText("Ningún dentista seleccionado");
            alerta.setContentText("Por favor, selecciona un dentista de la lista.");
            alerta.showAndWait();
            return;
        }

        try {
            logger.info("Intentando eliminar el dentista: " + nombreSeleccionado);
            Dentista dentista = dentistaDAO.findByNameEager(nombreSeleccionado);
            if (dentista == null) {
                throw new DentistaNoEncontradoException("No se encontró el dentista con el nombre: " + nombreSeleccionado);
            }

            dentistaDAO.deleteById(dentista.getIdDentista());
            logger.info("Dentista eliminado correctamente.");

            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Éxito");
            alerta.setHeaderText("Dentista eliminado");
            alerta.setContentText("El dentista ha sido eliminado correctamente.");
            alerta.showAndWait();

            cargarDentistas(); // Actualiza la lista
        } catch (DentistaNoEncontradoException e) {
            logger.warning(e.getMessage());
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Dentista no encontrado");
            alerta.setContentText(e.getMessage());
            alerta.showAndWait();
        } catch (Exception e) {
            logger.severe("Error al eliminar el dentista: " + e.getMessage());
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Error al eliminar el dentista");
            alerta.setContentText("Ocurrió un error al intentar eliminar el dentista.");
            alerta.showAndWait();
        }
    }

    @FXML
    private void editarDentista() {
        String nombreSeleccionado = dentistaListView.getSelectionModel().getSelectedItem();

        if (nombreSeleccionado == null) {
            logger.warning("No se seleccionó ningún dentista.");
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Advertencia");
            alerta.setHeaderText("Ningún dentista seleccionado");
            alerta.setContentText("Por favor, selecciona un dentista de la lista.");
            alerta.showAndWait();
            return;
        }

        try {
            logger.info("Cargando datos del dentista para editar: " + nombreSeleccionado);
            Dentista dentista = dentistaDAO.findByNameEager(nombreSeleccionado);
            if (dentista == null) {
                throw new DentistaNoEncontradoException("No se encontró el dentista con el nombre: " + nombreSeleccionado);
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dentistaEditForm.fxml"));
            Parent root = loader.load();

            // Obtener el controlador del formulario
            DentistaFormController formController = loader.getController();
            formController.setDentistaController(this); // Pasar referencia del controlador actual
            formController.cargarDatosDentista(dentista); // Cargar datos del dentista

            Stage stage = new Stage();
            stage.setTitle("Editar Dentista");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            logger.severe("Error al abrir el formulario de edición: " + e.getMessage());
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("No se pudo abrir el formulario");
            alerta.setContentText("Ocurrió un error al intentar abrir el formulario de edición.");
            alerta.showAndWait();
        } catch (DentistaNoEncontradoException e) {
            logger.warning(e.getMessage());
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Dentista no encontrado");
            alerta.setContentText(e.getMessage());
            alerta.showAndWait();
        }
    }
}