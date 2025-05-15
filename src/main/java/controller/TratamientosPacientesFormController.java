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

    public void setTratatamientosPacientesController(TratamientosPacientesController tratamientoPacienteController) {
        this.tratamientoPacienteController = tratamientoPacienteController;
    }

public void setTratamientoPaciente(TratamientoPaciente tratamientoPaciente) {
    this.tratamientoPaciente = tratamientoPaciente;

    // Rellenar los campos con los datos del tratamiento seleccionado
    idPaciente.setValue(PacienteDAO.getInstance().findByIdEager(tratamientoPaciente.getIdPaciente()));
    idTratamiento.setValue(TratamientoDAO.getInstance().findDescripcionById(tratamientoPaciente.getIdTratamiento()));
    detalles.setText(tratamientoPaciente.getDetalles());
}

    @FXML
    private void initialize() {
        configurarComboBoxPaciente();
        configurarComboBoxTratamiento();
    }

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
            LocalDate fechaTratamiento = LocalDate.now();

            if (detalles.getText() == null || detalles.getText().isEmpty()) {
                throw new DetallesVaciosException("El campo detalles no puede estar vacío.");
            }

            // Crear el objeto TratamientoPaciente
            Paciente paciente = idPaciente.getValue();
            String descripcionTratamiento = idTratamiento.getValue();
            Tratamiento tratamiento = TratamientoDAO.getInstance().findByDescripcionEager(descripcionTratamiento);

            TratamientoPaciente tratamientoPaciente = new TratamientoPaciente(
                paciente.getIdPaciente(),
                tratamiento.getIdTratamiento(),
                fechaTratamiento,
                detalles.getText()
            );

            // Guardar en la base de datos
            TratamientoPacienteDAO.getInstance().insert(tratamientoPaciente);

            // Mostrar mensaje de éxito
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Éxito");
            alerta.setHeaderText("Tratamiento añadido");
            alerta.setContentText("El tratamiento se ha añadido correctamente.");
            alerta.showAndWait();

            // Actualizar la lista en el controlador principal
            if (tratamientoPacienteController != null) {
                tratamientoPacienteController.cargarTratamientosPacientes();
            }

            // Cerrar la ventana
            cerrarVentana(event);
        }catch (PacienteNoSeleccionadoException e){
            logger.log(Level.SEVERE, "Error al guardar el tratamiento a paciente: " + e.getMessage(), e);
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Error al añadir el tratamiento");
            alerta.setContentText("Debe seleccionarse un paciente.");
            alerta.showAndWait();
        }
        catch (DetallesVaciosException e) {
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
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, "Error al guardar el tratamiento a paciente: " + e.getMessage(), e);
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("No se pudo guardar el tratamiento");
            alerta.setContentText(e.getMessage());
            alerta.showAndWait();
        }
    }

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
