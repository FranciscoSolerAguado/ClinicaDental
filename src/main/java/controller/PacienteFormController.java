package controller;

import DAO.PacienteDAO;
import exceptions.DNIErroneoException;
import exceptions.FechaNVaciaException;
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

    private final PacienteDAO pacienteDAO = PacienteDAO.getInstance();
    private PacienteController pacienteController;
    private final static Logger logger = Logger.getLogger(PacienteFormController.class.getName());

    /**
     * Establece el controlador principal para actualizar la lista de pacientes.
     * @param pacienteController Controlador principal.
     */
    public void setPacienteController(PacienteController pacienteController) {
        this.pacienteController = pacienteController;
    }

    /**
     * Guarda los datos del paciente, ya sea creando uno nuevo o actualizando uno existente.
     * @param event Evento que dispara la acción.
     */
    @FXML
    private void guardarPaciente(ActionEvent event) {
        logger.info("Iniciando el proceso de guardar un paciente...");
        try {
            String nombre = nombreField.getText();
            String dni = dniField.getText();
            if (!dni.matches("^[0-9]{8}[A-HJ-NP-TV-Z]$")) { //EXPRESION REGULAR DNI
                throw new DNIErroneoException("DNI incorrecto. Debe tener 8 cifras y una letra al final.");
            }

            String telefonoStr = telefonoField.getText();
            if (!telefonoStr.matches("^[67][0-9]{8}$")) { //EXPRESION REGULAR TELEFONO
                throw new TelefonoErroneoException("Teléfono incorrecto. Debe tener 9 cifras y comenzar por 6 o 7.");
            }
            int telefono = Integer.parseInt(telefonoStr);

            String alergias = alergiasField.getText();

            LocalDate fechaNacimiento = fechaNacimientoPicker.getValue();
            if (fechaNacimiento == null) {
                throw new FechaNVaciaException("La fecha de nacimiento no puede estar vacía.");
            }
            int edad = Period.between(fechaNacimiento, LocalDate.now()).getYears(); //La edad se calcula a partir de la fecha de nacimiento

            if (pacienteActual != null) {
                // Actualizar paciente existente
                pacienteActual.setNombre(nombre);
                pacienteActual.setDni(dni);
                pacienteActual.setTelefono(telefono);
                pacienteActual.setFechaNacimiento(fechaNacimiento);
                pacienteActual.setAlergias(alergiasField.getText());
                pacienteActual.setEdad(edad);

                pacienteDAO.update(pacienteActual.getIdPaciente(), pacienteActual);
                logger.info("Paciente actualizado correctamente.");
            } else {
                // Crear un nuevo paciente
                Paciente nuevoPaciente = new Paciente(nombre, dni, telefono, 0, alergias, fechaNacimiento, edad, null);
                pacienteDAO.insert(nuevoPaciente);
                logger.info("Paciente añadido correctamente.");
            }

            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Éxito");
            alerta.setHeaderText("Paciente guardado");
            alerta.setContentText("El paciente se ha guardado correctamente.");
            alerta.showAndWait();

            if (pacienteController != null) {
                pacienteController.cargarPacientes(); // Actualizar la lista
            }

            cerrarVentana(event);
        }catch (DNIErroneoException e){
            logger.log(Level.SEVERE, "Error al guardar el paciente: " + e.getMessage(), e);
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("DNI inválido");
            alerta.setContentText(e.getMessage());
            alerta.showAndWait();
        }
        catch (TelefonoErroneoException e){
            logger.log(Level.SEVERE, "Error al guardar el paciente: " + e.getMessage(), e);
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Teléfono inválido");
            alerta.setContentText(e.getMessage());
            alerta.showAndWait();
        }
        catch (FechaNVaciaException e){
            logger.log(Level.SEVERE, "Error al guardar el paciente: " + e.getMessage(), e);
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("La fecha de nacimiento no puede estar vacia");
            alerta.setContentText(e.getMessage());
            alerta.showAndWait();
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, "Error al guardar el paciente: " + e.getMessage(), e);
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Error al añadir el paciente");
            alerta.setContentText("Por favor, verifica los datos ingresados.");
            alerta.showAndWait();
        }
    }

    /**
     * Cancela la operación y cierra la ventana.
     * @param event realiza la acción.
     */
    @FXML
    private void cancelar(ActionEvent event) {
        logger.info("Cancelando la operación y cerrando la ventana.");
        cerrarVentana(event);
    }

    /**
     * Cierra la ventana actual.
     * @param event realiza la acción.
     */
    private void cerrarVentana(ActionEvent event) {
        logger.info("Cerrando la ventana del formulario de paciente.");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private Paciente pacienteActual;

    /**
     * Carga los datos de un paciente existente en el formulario.
     * @param paciente Paciente a cargar.
     */
    public void cargarDatosPaciente(Paciente paciente) {
        this.pacienteActual = paciente;
        nombreField.setText(paciente.getNombre());
        dniField.setText(paciente.getDni());
        telefonoField.setText(String.valueOf(paciente.getTelefono()));
        alergiasField.setText(paciente.getAlergias());
        fechaNacimientoPicker.setValue(paciente.getFechaNacimiento());
    }
}
