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

import java.io.IOException;

public class TratamientoController {
    @FXML
    private ListView<String> tratamientoListView;
    private final TratamientoDAO tratamientoDAO = TratamientoDAO.getInstance();
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(TratamientoController.class.getName());

    /**
     * Método que se ejecuta al inicializar la vista.
     * Carga los nombres de los tratamientos en el ListView.
     */
    @FXML
    public void initialize() {
        // Cargar los nombres de los tratamientos en el ListView
        tratamientoDAO.findAll().forEach(tratamiento -> {
            if (tratamiento instanceof Tratamiento) {
                tratamientoListView.getItems().add(((Tratamiento) tratamiento).getDescripcion());
            }
        });
    }


    /**
     * Vuelve a la vista principal.
     * @param event realiza el cambio de vista.
     * @throws IOException Si ocurre un error al cargar el archivo FXML.
     */
    @FXML
    private void volverAMain(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Obtiene la ventana (Stage) actual a partir del evento
        Scene scene = new Scene(root);
        stage.setScene(scene);    // Establece la nueva escena en la ventana
        stage.setMaximized(true); // Maximizar la ventana con bordes
        stage.show();    // Muestra la ventana
    }

    /**
     * Recarga la lista de tratamientos en el ListView.
     */
    @FXML
    public void cargarTratamientos() {
        try {
            tratamientoListView.getItems().clear(); // Limpia la lista antes de cargar los tratamientos
            tratamientoDAO.findAll().forEach(tratamiento -> {
                if (tratamiento instanceof Tratamiento) { // Verifica que el objeto sea una instancia de Tratamiento
                    tratamientoListView.getItems().add(((Tratamiento) tratamiento).getDescripcion()); // Agrega la descripción del tratamiento a la lista
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

    /**
     * Muestra información detallada del tratamiento seleccionado.
     */
    @FXML
    private void mostrarMas() {
        logger.info("Intentando mostrar información del tratamiento seleccionado...");

        String descripcionSeleccionada = tratamientoListView.getSelectionModel().getSelectedItem(); // Obtiene el tratamiento seleccionado en la lista

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
            Tratamiento tratamiento = tratamientoDAO.findByDescripcionEager(descripcionSeleccionada);
            if (tratamiento == null) {
                throw new TratamientoNoEncontradoException("No se encontró el tratamiento con la descripción: " + descripcionSeleccionada);
            }

            String dentistaNombre = (tratamiento.getDentista() != null) ? tratamiento.getDentista().getNombre() : "No asignado"; // Verifica si el dentista está asignado a un tratamiento

            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Información del Tratamiento");
            alerta.setHeaderText("Detalles del tratamiento seleccionado");
            alerta.setContentText(
                    "idTratamiento: " + tratamiento.getIdTratamiento() + "\n" +
                            "Descripción: " + tratamiento.getDescripcion() + "\n" +
                            "Precio: " + tratamiento.getPrecio() + "\n" +
                            "Dentista especialista: " + dentistaNombre + "\n"
            );
            alerta.showAndWait();
        } catch (TratamientoNoEncontradoException e) { // Manejo de excepción personalizada
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

    /**
     * Abre el formulario para añadir un nuevo tratamiento.
     */
    @FXML
    private void abrirFormularioTratamiento() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/tratamientoForm.fxml"));
            Parent root = loader.load();

            // Obtener el controlador del formulario
            TratamientoFormController formController = loader.getController();
            formController.setTratamientoController(this); // Pasar referencia del controlador actual

            Stage stage = new Stage(); // Crear una nueva ventana
            stage.setTitle("Añadir Tratamiento");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            logger.severe("Error al abrir el formulario de editar tratamiento: " + e.getMessage());
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("No se pudo abrir el formulario");
            alerta.setContentText("Ocurrió un error al intentar abrir el formulario de editar tratamiento.");
            alerta.showAndWait();
        }
    }

    /**
     * Elimina el tratamiento seleccionado.
     */
    @FXML
    private void eliminarTratamiento() {
        String descripcionSeleccionada = tratamientoListView.getSelectionModel().getSelectedItem(); // Coje el tratamiento seleccionado en la lista

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
            logger.info("Intentando eliminar el tratamiento: " + descripcionSeleccionada);
            Tratamiento tratamiento = tratamientoDAO.findByDescripcionEager(descripcionSeleccionada);
            if (tratamiento == null) {
                throw new TratamientoNoEncontradoException("No se encontró el tratamiento con la descripción: " + descripcionSeleccionada);
            }

            tratamientoDAO.deleteById(tratamiento.getIdTratamiento());
            logger.info("Tratamiento eliminado correctamente.");

            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Éxito");
            alerta.setHeaderText("Tratamiento eliminado");
            alerta.setContentText("El tratamiento ha sido eliminado correctamente.");
            alerta.showAndWait();

            cargarTratamientos(); // Actualiza la lista
        } catch (TratamientoNoEncontradoException e) {// Manejo de excepción personalizada
            logger.warning(e.getMessage());
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Tratamiento no encontrado");
            alerta.setContentText(e.getMessage());
            alerta.showAndWait();
        } catch (Exception e) { // Manejo de excepción general
            logger.severe("Error al eliminar el tratamiento: " + e.getMessage());
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Error al eliminar el tratamiento");
            alerta.setContentText("Ocurrió un error al intentar eliminar el tratamiento.");
            alerta.showAndWait();
        }
    }

    /**
     * Abre el formulario para editar el tratamiento seleccionado.
     */
    @FXML
    private void editarTratamiento() {
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
            logger.info("Cargando datos del tratamiento para editar: " + descripcionSeleccionada);
            Tratamiento tratamiento = tratamientoDAO.findByDescripcionEager(descripcionSeleccionada);
            if (tratamiento == null) {
                throw new TratamientoNoEncontradoException("No se encontró el tratamiento con la descripción: " + descripcionSeleccionada);
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/tratamientoEditForm.fxml"));
            Parent root = loader.load();

            // Obtener el controlador del formulario
            TratamientoFormController formController = loader.getController();
            formController.setTratamientoController(this); // Pasar referencia del controlador actual
            formController.cargarDatosTratamiento(tratamiento); // Cargar datos del tratamiento

            Stage stage = new Stage(); // Crear una nueva ventana
            stage.setTitle("Editar Tratamiento");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            logger.severe("Error al abrir el formulario de edición: " + e.getMessage());
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("No se pudo abrir el formulario");
            alerta.setContentText("Ocurrió un error al intentar abrir el formulario de edición.");
            alerta.showAndWait();
        } catch (TratamientoNoEncontradoException e) { // Manejo de excepción personalizada
            logger.warning(e.getMessage());
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Tratamiento no encontrado");
            alerta.setContentText(e.getMessage());
            alerta.showAndWait();
        }
    }
}
