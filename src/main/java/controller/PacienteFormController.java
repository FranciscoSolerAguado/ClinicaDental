package controller;

import DAO.PacienteDAO;
import exceptions.DNIErroneoException;
import exceptions.TelefonoErroneoException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Paciente;

import java.time.LocalDate;
import java.time.Period;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PacienteFormController {

    @FXML
    private TextField nombreField;
    @FXML
    private TextField dniField;
    @FXML
    private TextField telefonoField;
    @FXML
    private TextField alergiasField;
    @FXML
    private DatePicker fechaNacimientoPicker;
    @FXML
    private TextField edadField;

    private final PacienteDAO pacienteDAO = PacienteDAO.getInstance();
    private PacienteController pacienteController;
    private final static Logger logger = Logger.getLogger(PacienteFormController.class.getName());

    public void setPacienteController(PacienteController pacienteController) {
        this.pacienteController = pacienteController;
    }

    @FXML
    private void guardarPaciente(ActionEvent event) {
        logger.info("Iniciando el proceso de guardar un nuevo paciente...");
        try {
            String nombre = nombreField.getText();
            logger.fine("Nombre ingresado: " + nombre);

            // Validar el DNI
            String dni = dniField.getText();
            logger.fine("Validando DNI: " + dni);
            if (!dni.matches("^[0-9]{8}[A-HJ-NP-TV-Z]$")) {
                logger.warning("DNI inválido: " + dni);
                throw new DNIErroneoException("DNI incorrecto. Debe tener 8 cifras y una letra al final.");
            }

            // Validar el teléfono
            String telefonoStr = telefonoField.getText();
            logger.fine("Validando teléfono: " + telefonoStr);
            if (!telefonoStr.matches("^[67][0-9]{8}$")) {
                logger.warning("Teléfono inválido: " + telefonoStr);
                throw new TelefonoErroneoException("Teléfono incorrecto. Debe tener 9 cifras y comenzar por 6 o 7.");
            }
            int telefono = Integer.parseInt(telefonoStr);


            String alergias = alergiasField.getText();
            logger.fine("Alergia ingresada: " + alergias);

            LocalDate fechaNacimiento = fechaNacimientoPicker.getValue();
            logger.fine("Fecha de nacimiento seleccionada: " + fechaNacimiento);

            // Calcular la edad automáticamente
            if (fechaNacimiento == null) {
                logger.warning("La fecha de nacimiento no puede estar vacía.");
                throw new IllegalArgumentException("La fecha de nacimiento no puede estar vacía.");
            }
            int edad = Period.between(fechaNacimiento, LocalDate.now()).getYears();
            logger.fine("Edad calculada: " + edad);

            Paciente nuevoPaciente = new Paciente(nombre, dni, telefono, 0, alergias, fechaNacimiento, edad, null);
            logger.info("Insertando el nuevo paciente en la base de datos: " + nuevoPaciente);
            pacienteDAO.insert(nuevoPaciente);

            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Éxito");
            alerta.setHeaderText("Paciente añadido");
            alerta.setContentText("El paciente se ha añadido correctamente.");
            alerta.showAndWait();

            logger.info("Paciente añadido correctamente. Actualizando la lista de pacientes...");
            if (pacienteController != null) {
                pacienteController.cargarPacientes();
            }

            cerrarVentana(event);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al guardar el paciente: " + e.getMessage(), e);
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Error al añadir el paciente");
            alerta.setContentText("Por favor, verifica los datos ingresados.");
            alerta.showAndWait();
        }
    }

    @FXML
    private void cancelar(ActionEvent event) {
        logger.info("Cancelando la operación y cerrando la ventana.");
        cerrarVentana(event);
    }

    private void cerrarVentana(ActionEvent event) {
        logger.info("Cerrando la ventana del formulario de paciente.");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
