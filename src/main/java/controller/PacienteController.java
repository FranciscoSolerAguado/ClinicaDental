package controller;

import DAO.PacienteDAO;
import DAO.TratamientoDAO;
import DAO.TratamientoPacienteDAO;
import exceptions.PacienteNoEncontradoException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.Paciente;

import java.io.IOException;
import java.util.logging.Logger;

public class PacienteController {
    @FXML
    private ListView<String> pacienteListView;
    private final PacienteDAO pacienteDAO = PacienteDAO.getInstance();
    private static final Logger logger = Logger.getLogger(PacienteController.class.getName());

    /**
     * Método que se ejecuta al inicializar la vista.
     * Carga los nombres de los pacientes en el ListView.
     */
@FXML
public void initialize() {
    TratamientoDAO tratamientoDAO = TratamientoDAO.getInstance();
    TratamientoPacienteDAO tratamientoPacienteDAO = TratamientoPacienteDAO.getInstance();

    // Inicializar las dependencias de PacienteDAO
    pacienteDAO.initialize(tratamientoDAO, tratamientoPacienteDAO);

    // Cargar los nombres de los pacientes en el ListView
    pacienteDAO.findAll().forEach(paciente -> {
        if (paciente instanceof Paciente) { // Verifica que el objeto sea de tipo Paciente
            pacienteListView.getItems().add(((Paciente) paciente).getNombre()); // En el list view se muestra el nombre del paciente
        }
    });
}

    /**
     * Vuelve a la vista principal.
     *
     * @throws IOException Si ocurre un error al cargar el archivo FXML.
     */
    @FXML
    private void volverAMain() {
        try {
            // Cargar la vista principal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
            Parent root = loader.load();

            // Obtener la escena actual y cambiar el contenido
            Scene scene = pacienteListView.getScene();
            scene.setRoot(root);
        } catch (IOException e) {
            logger.severe("Error al cargar la vista principal: " + e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * Recarga la lista de pacientes en el ListView.
     */
    @FXML
    public void cargarPacientes() {
        try {
            pacienteListView.getItems().clear();
            pacienteDAO.findAll().forEach(paciente -> {
                if (paciente instanceof Paciente) { // Verifica que la instancia sea de tipo Paciente
                    pacienteListView.getItems().add(((Paciente) paciente).getNombre());
                }
            });
            logger.info("Lista de pacientes cargada correctamente.");
        } catch (Exception e) {
            logger.severe("Error al cargar la lista de pacientes: " + e.getMessage());
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Error al cargar la lista de pacientes");
            alerta.setContentText("No se pudo cargar la lista de pacientes. Intenta nuevamente.");
            alerta.showAndWait();
        }
    }

    /**
     * Muestra información detallada del paciente seleccionado.
     */
    @FXML
    private void mostrarMas() {
        logger.info("Intentando mostrar información del paciente seleccionado...");

        String nombreSeleccionado = pacienteListView.getSelectionModel().getSelectedItem();

        if (nombreSeleccionado == null) {
            logger.warning("No se seleccionó ningún paciente.");
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Advertencia");
            alerta.setHeaderText("Ningún paciente seleccionado");
            alerta.setContentText("Por favor, selecciona un paciente de la lista.");
            alerta.showAndWait();
            return;
        }

        try {
            logger.info("Buscando información del paciente: " + nombreSeleccionado);
            Paciente paciente = pacienteDAO.findByNameEager(nombreSeleccionado);
            if (paciente == null) {
                throw new PacienteNoEncontradoException("No se encontró el paciente con el nombre: " + nombreSeleccionado);
            }

            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Información del Paciente");
            alerta.setHeaderText("Detalles del paciente seleccionado");
            alerta.setContentText(
                    "Nombre: " + paciente.getNombre() + "\n" +
                            "DNI: " + paciente.getDni() + "\n" +
                            "Teléfono: " + paciente.getTelefono() + "\n" +
                            "idPaciente: " + paciente.getIdPaciente() + "\n" +
                            "Fecha de Nacimiento: " + paciente.getFechaNacimiento() + "\n" +
                            "Edad: " + paciente.getEdad() + "\n" +
                            "Alergias: " + paciente.getAlergias() + "\n"
            );
            alerta.showAndWait();
        } catch (PacienteNoEncontradoException e) { // Manejo de excepción personalizada
            logger.warning(e.getMessage());
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Paciente no encontrado");
            alerta.setContentText(e.getMessage());
            alerta.showAndWait();
        } catch (Exception e) { // Manejo de excepciones generales
            logger.severe("Error al buscar el paciente: " + e.getMessage());
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Error al buscar el paciente");
            alerta.setContentText("Ocurrió un error al buscar el paciente seleccionado.");
            alerta.showAndWait();
        }
    }

    /**
     * Abre el formulario para añadir un nuevo paciente.
     */
    @FXML
    private void abrirFormularioPaciente() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/pacienteForm.fxml"));
            Parent root = loader.load();

            // Obtener el controlador del formulario
            PacienteFormController formController = loader.getController();
            formController.setPacienteController(this); // Pasar referencia del controlador actual

            Stage stage = new Stage();
            stage.setTitle("Añadir Paciente");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            logger.severe("Error al abrir el formulario de añadir paciente: " + e.getMessage());
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("No se pudo abrir el formulario");
            alerta.setContentText("Ocurrió un error al intentar abrir el formulario de añadir paciente.");
            alerta.showAndWait();
        }
    }

    /**
     * Elimina el paciente seleccionado.
     */
    @FXML
    private void eliminarPaciente() {
        String nombreSeleccionado = pacienteListView.getSelectionModel().getSelectedItem();

        if (nombreSeleccionado == null) {
            logger.warning("No se seleccionó ningún paciente.");
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Advertencia");
            alerta.setHeaderText("Ningún paciente seleccionado");
            alerta.setContentText("Por favor, selecciona un paciente de la lista.");
            alerta.showAndWait();
            return;
        }

        try {
            logger.info("Intentando eliminar el paciente: " + nombreSeleccionado);
            Paciente paciente = pacienteDAO.findByNameEager(nombreSeleccionado);
            if (paciente == null) {
                throw new PacienteNoEncontradoException("No se encontró el paciente con el nombre: " + nombreSeleccionado);
            }

            pacienteDAO.deleteById(paciente.getIdPaciente());
            logger.info("Paciente eliminado correctamente.");

            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Éxito");
            alerta.setHeaderText("Paciente eliminado");
            alerta.setContentText("El paciente ha sido eliminado correctamente.");
            alerta.showAndWait();

            cargarPacientes(); // Actualiza la lista
        } catch (PacienteNoEncontradoException e) {
            logger.warning(e.getMessage());
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Paciente no encontrado");
            alerta.setContentText(e.getMessage());
            alerta.showAndWait();
        } catch (Exception e) {
            logger.severe("Error al eliminar el paciente: " + e.getMessage());
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Error al eliminar el paciente");
            alerta.setContentText("Ocurrió un error al intentar eliminar el paciente.");
            alerta.showAndWait();
        }
    }

    /**
     * Elimina el paciente seleccionado.
     */
    @FXML
    private void editarPaciente() {
        String nombreSeleccionado = pacienteListView.getSelectionModel().getSelectedItem();

        if (nombreSeleccionado == null) {
            logger.warning("No se seleccionó ningún paciente.");
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Advertencia");
            alerta.setHeaderText("Ningún paciente seleccionado");
            alerta.setContentText("Por favor, selecciona un paciente de la lista.");
            alerta.showAndWait();
            return;
        }

        try {
            logger.info("Cargando datos del paciente para editar: " + nombreSeleccionado);
            Paciente paciente = pacienteDAO.findByNameEager(nombreSeleccionado);
            if (paciente == null) {
                throw new PacienteNoEncontradoException("No se encontró el paciente con el nombre: " + nombreSeleccionado);
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/pacienteEditForm.fxml"));
            Parent root = loader.load();

            // Obtener el controlador del formulario
            PacienteFormController formController = loader.getController();
            formController.setPacienteController(this); // Pasar referencia del controlador actual
            formController.cargarDatosPaciente(paciente); // Cargar datos del paciente

            Stage stage = new Stage();
            stage.setTitle("Editar Paciente");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            logger.severe("Error al abrir el formulario de edición: " + e.getMessage());
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("No se pudo abrir el formulario");
            alerta.setContentText("Ocurrió un error al intentar abrir el formulario de edición.");
            alerta.showAndWait();
        } catch (PacienteNoEncontradoException e) {
            logger.warning(e.getMessage());
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Paciente no encontrado");
            alerta.setContentText(e.getMessage());
            alerta.showAndWait();
        }
    }


    /**
     * Muestra los tratamientos del paciente seleccionado.
     */
    @FXML
    private void mostrarTratamientosPaciente() {
        String nombreSeleccionado = pacienteListView.getSelectionModel().getSelectedItem();

        if (nombreSeleccionado == null) {
            logger.warning("No se seleccionó ningún paciente.");
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Advertencia");
            alerta.setHeaderText("Ningún paciente seleccionado");
            alerta.setContentText("Por favor, selecciona un paciente de la lista.");
            alerta.showAndWait();
            return;
        }

        try {
            logger.info("Cargando tratamientos del paciente: " + nombreSeleccionado);
            Paciente paciente = pacienteDAO.findByNameEager(nombreSeleccionado); // Carga el paciente con tratamientos
            if (paciente == null) {
                throw new PacienteNoEncontradoException("No se encontró el paciente con el nombre: " + nombreSeleccionado);
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/pacienteTratamientos.fxml"));
            Parent root = loader.load();

            PacienteTratamientoController controller = loader.getController();
            controller.setPaciente(paciente); // Pasa el paciente cargado al controlador

            Stage stage = new Stage();
            stage.setTitle("Tratamientos del Paciente");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            logger.severe("Error al abrir la ventana de tratamientos: " + e.getMessage());
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("No se pudo abrir la ventana");
            alerta.setContentText("Ocurrió un error al intentar abrir la ventana de tratamientos.");
            alerta.showAndWait();
        } catch (PacienteNoEncontradoException e) {
            logger.warning(e.getMessage());
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Paciente no encontrado");
            alerta.setContentText(e.getMessage());
            alerta.showAndWait();
        }
    }
}