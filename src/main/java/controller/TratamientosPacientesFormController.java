package controller;

import DAO.PacienteDAO;
import DAO.TratamientoDAO;
import DAO.TratamientoPacienteDAO;
import exceptions.DetallesVaciosException;
import exceptions.PacienteNoSeleccionadoException;
import exceptions.TratamientoNoSeleccionadoException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Paciente;
import model.Tratamiento;
import model.TratamientoPaciente;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TratamientosPacientesFormController {
    @FXML
    private ComboBox<Paciente> idPaciente;

    @FXML
    private ComboBox<String> idTratamiento;

    @FXML
    private TextField detalles;


    private static final Logger logger = Logger.getLogger(TratamientosPacientesFormController.class.getName());
    private TratamientosPacientesController tratamientoPacienteController;
    private TratamientoPaciente tratamientoPaciente;

    /**
     * Establece el controlador principal para actualizar la lista de tratamientos.
     *
     * @param tratamientoPacienteController Controlador principal.
     */
    public void setTratatamientosPacientesController(TratamientosPacientesController tratamientoPacienteController) {
        this.tratamientoPacienteController = tratamientoPacienteController;
    }

    /**
     * Establece el tratamiento de paciente a editar y rellena los campos del formulario.
     *
     * @param tratamientoPaciente TratamientoPaciente cuyos datos se cargarán.
     */
    public void setTratamientoPaciente(TratamientoPaciente tratamientoPaciente) {
        this.tratamientoPaciente = tratamientoPaciente;

        // Rellenar los campos con los datos del tratamiento seleccionado
        idPaciente.setValue(tratamientoPaciente.getPaciente());
        idTratamiento.setValue(tratamientoPaciente.getTratamiento().getDescripcion());
        detalles.setText(tratamientoPaciente.getDetalles());
    }


    /**
     * Método que se ejecuta al inicializar la vista.
     * Configura los ComboBox y carga los datos necesarios.
     */
    @FXML
    private void initialize() {
        configurarComboBoxPaciente();
        configurarComboBoxTratamiento();
    }

    /**
     * Configura el ComboBox de pacientes para mostrar el nombre del paciente.
     * De normal mostraria el idPaciente, pero se ha cambiado para mostrar el nombre del paciente.
     */
    private void configurarComboBoxPaciente() {
        idPaciente.setCellFactory(comboBox -> new ListCell<>() {
            @Override
            protected void updateItem(Paciente paciente, boolean empty) {
                super.updateItem(paciente, empty);
                setText(empty || paciente == null ? null : paciente.getNombre());
            }
        });

        idPaciente.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Paciente paciente, boolean empty) {
                super.updateItem(paciente, empty);
                setText(empty || paciente == null ? null : paciente.getNombre());
            }
        });

        cargarPacientes();
    }

    /**
     * Configura el ComboBox de tratamientos para mostrar la descripción del tratamiento.
     * De normal mostraria el idTratamiento, pero se ha cambiado para mostrar la descripcion del tratamiento.
     */
    private void configurarComboBoxTratamiento() {
        idTratamiento.setCellFactory(comboBox -> new ListCell<>() {
            @Override
            protected void updateItem(String tratamiento, boolean empty) {
                super.updateItem(tratamiento, empty);
                setText(empty || tratamiento == null ? null : tratamiento);
            }
        });

        idTratamiento.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(String tratamiento, boolean empty) {
                super.updateItem(tratamiento, empty);
                setText(empty || tratamiento == null ? null : tratamiento);
            }
        });

        cargarTratamientos();
    }

    /**
     * Carga los pacientes desde la base de datos y los añade al ComboBox.
     */
    private void cargarPacientes() {
        try {
            List<Object> pacientes = PacienteDAO.getInstance().findAll();
            for (Object obj : pacientes) {
                if (obj instanceof Paciente) {
                    idPaciente.getItems().add((Paciente) obj);
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al cargar los pacientes: " + e.getMessage(), e);
        }
    }

    /**
     * Carga los tratamientos desde la base de datos y los añade al ComboBox (Lista de seleccion)
     */
    private void cargarTratamientos() {
        try {
            List<Object> tratamientos = TratamientoDAO.getInstance().findAll();
            for (Object obj : tratamientos) {
                if (obj instanceof Tratamiento) {
                    idTratamiento.getItems().add(((Tratamiento) obj).getDescripcion());
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al cargar los tratamientos: " + e.getMessage(), e);
        }
    }

    /**
     * Guarda el tratamiento del paciente en la base de datos.
     *
     * @param event Evento de acción.
     */
    @FXML
    private void guardarTratamientoPaciente(ActionEvent event) {
        try {
            // Validar campos
            if (idPaciente.getValue() == null) {
                throw new PacienteNoSeleccionadoException("Debe seleccionar un paciente.");
            }
            if (idTratamiento.getValue() == null || idTratamiento.getValue().isEmpty()) {
                throw new TratamientoNoSeleccionadoException("Debe seleccionar un tratamiento.");
            }
            if (detalles.getText() == null || detalles.getText().isEmpty()) {
                throw new DetallesVaciosException("El campo detalles no puede estar vacío.");
            }

            // Obtener datos del form
            Paciente paciente = idPaciente.getValue();
            String descripcionTratamiento = idTratamiento.getValue();
            Tratamiento tratamiento = TratamientoDAO.getInstance().findByDescripcionEager(descripcionTratamiento);

            // Si el objeto tratamientoPaciente ya existe, actualiza
            if (tratamientoPaciente != null) {
                tratamientoPaciente.setPaciente(paciente);
                tratamientoPaciente.setTratamiento(tratamiento);
                tratamientoPaciente.setDetalles(detalles.getText());

                TratamientoPacienteDAO.getInstance().update(tratamientoPaciente);
            } else {
                // Si no existe, crea uno nuevo
                TratamientoPaciente nuevoTratamientoPaciente = new TratamientoPaciente(
                        paciente,
                        tratamiento,
                        LocalDate.now(),
                        detalles.getText()
                );
                TratamientoPacienteDAO.getInstance().insert(nuevoTratamientoPaciente);
            }

            // Mostrar mensaje de éxito
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Éxito");
            alerta.setHeaderText("Tratamiento guardado");
            alerta.setContentText("El tratamiento se ha guardado correctamente.");
            alerta.showAndWait();

            // Actualizar la lista en el controlador principal
            if (tratamientoPacienteController != null) {
                tratamientoPacienteController.cargarTratamientosPacientes();
            }

            // Cerrar la ventana
            cerrarVentana(event);
        } catch (PacienteNoSeleccionadoException e) {
            logger.log(Level.SEVERE, "Error al guardar el tratamiento a paciente: " + e.getMessage(), e);
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Error al añadir el tratamiento");
            alerta.setContentText("Debe seleccionarse un paciente.");
            alerta.showAndWait();
        } catch (DetallesVaciosException e) {
            logger.log(Level.SEVERE, "Error al guardar el tratamiento a paciente: " + e.getMessage(), e);
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Error al añadir el tratamiento");
            alerta.setContentText("El campo detalles no puede estar vacío.");
            alerta.showAndWait();
        } catch (TratamientoNoSeleccionadoException e) {
            logger.log(Level.SEVERE, "Error al guardar el tratamiento a paciente: " + e.getMessage(), e);
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Error al añadir el tratamiento");
            alerta.setContentText("Debe seleccionarse un tratamiento.");
            alerta.showAndWait();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al guardar el tratamiento a paciente: " + e.getMessage(), e);
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("No se pudo guardar el tratamiento");
            alerta.setContentText(e.getMessage());
            alerta.showAndWait();
        }
    }

    /**
     * Método para manejar el evento de cancelar la operación.
     *
     * @param event Evento de acción.
     */
    @FXML
    private void cancelar(ActionEvent event) {
        logger.info("Cancelando la operación y cerrando la ventana.");
        cerrarVentana(event);
    }

    private void cerrarVentana(ActionEvent event) {
        logger.info("Cerrando la ventana del formulario de tratamiento.");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
