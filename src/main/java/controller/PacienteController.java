package controller;

import DAO.PacienteDAO;
import DAO.PacienteDAO;
import DAO.TratamientoDAO;
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
import model.Paciente;
import model.Tratamiento;
import model.TratamientoPaciente;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class PacienteController {
    @FXML
    private ListView<String> pacienteListView;
    private final PacienteDAO pacienteDAO = PacienteDAO.getInstance();
    private static final Logger logger = Logger.getLogger(PacienteController.class.getName());

    @FXML
    public void initialize() {
        // Cargar los nombres de los pacientes en el ListView
        pacienteDAO.findAll().forEach(paciente -> {
            if (paciente instanceof Paciente) {
                pacienteListView.getItems().add(((Paciente) paciente).getNombre());
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
    public void cargarPacientes() {
        try {
            pacienteListView.getItems().clear();
            pacienteDAO.findAll().forEach(paciente -> {
                if (paciente instanceof Paciente) {
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
        } catch (PacienteNoEncontradoException e) {
            logger.warning(e.getMessage());
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Paciente no encontrado");
            alerta.setContentText(e.getMessage());
            alerta.showAndWait();
        } catch (Exception e) {
            logger.severe("Error al buscar el paciente: " + e.getMessage());
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Error al buscar el paciente");
            alerta.setContentText("Ocurrió un error al buscar el paciente seleccionado.");
            alerta.showAndWait();
        }
    }

    @FXML
    private void abrirFormularioPaciente() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pacienteForm.fxml"));
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

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pacienteEditForm.fxml"));
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

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pacienteTratamientos.fxml"));
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