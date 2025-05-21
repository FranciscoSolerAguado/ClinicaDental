package controller;

import DAO.DentistaDAO;
import exceptions.DNIErroneoException;
import exceptions.FechaNVaciaException;
import exceptions.NColegiadoErroneoException;
import exceptions.TelefonoErroneoException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Dentista;

import java.time.LocalDate;
import java.time.Period;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DentistaFormController {

    @FXML
    private TextField nombreField;
    @FXML
    private TextField dniField;
    @FXML
    private TextField telefonoField;
    @FXML
    private TextField nColegiadoField;
    @FXML
    private TextField especialidadField;
    @FXML
    private DatePicker fechaNacimientoPicker;

    private final DentistaDAO dentistaDAO = DentistaDAO.getInstance();
    private DentistaController dentistaController;
    private final static Logger logger = Logger.getLogger(DentistaFormController.class.getName());//Clase logger para registrar eventos

    /**
     * Establece el controlador principal para actualizar la lista de dentistas.
     *
     * @param dentistaController Controlador principal.
     */
    public void setDentistaController(DentistaController dentistaController) {
        this.dentistaController = dentistaController;
    }

    /**
     * Guarda un nuevo dentista o actualiza uno existente.
     *
     * @param event realiza el guardado.
     */
    @FXML
    private void guardarDentista(ActionEvent event) {
        logger.info("Iniciando el proceso de guardar un dentista...");
        try {
            String nombre = nombreField.getText();
            String dni = dniField.getText();
            if (!dni.matches("^[0-9]{8}[A-HJ-NP-TV-Z]$")) { // EXPRESIÓN REGULAR DNI
                throw new DNIErroneoException("DNI incorrecto. Debe tener 8 cifras y una letra al final.");
            }

            String telefonoStr = telefonoField.getText();
            if (!telefonoStr.matches("^[67][0-9]{8}$")) { // EXPRESIÓN REGULAR TELÉFONO
                throw new TelefonoErroneoException("Teléfono incorrecto. Debe tener 9 cifras y comenzar por 6 o 7.");
            }
            int telefono = Integer.parseInt(telefonoStr);

            String nColegiado = nColegiadoField.getText();
            if (!nColegiado.matches("^[0-9]{5}-[A-Z]$")) { // EXPRESIÓN REGULAR NÚMERO DE COLEGIADO
                throw new NColegiadoErroneoException("Número de colegiado incorrecto. Debe ser 5 dígitos seguidos de '-' y una letra mayúscula.");
            }

            String especialidad = especialidadField.getText();
            LocalDate fechaNacimiento = fechaNacimientoPicker.getValue();
            if (fechaNacimiento == null) {
                throw new FechaNVaciaException("La fecha de nacimiento no puede estar vacía.");
            }
            int edad = Period.between(fechaNacimiento, LocalDate.now()).getYears(); // La edad no es necesario guardarla, se calcula a partir de la fecha de nacimiento.

            if (dentistaActual != null) {
                // Actualizar dentista existente
                dentistaActual.setNombre(nombre);
                dentistaActual.setDni(dni);
                dentistaActual.setTelefono(telefono);
                dentistaActual.setnColegiado(nColegiado);
                dentistaActual.setEspecialidad(especialidad);
                dentistaActual.setFechaNacimiento(fechaNacimiento);
                dentistaActual.setEdad(edad);

                dentistaDAO.update(dentistaActual.getIdDentista(), dentistaActual);
                logger.info("Dentista actualizado correctamente.");
            } else {
                // Crear un nuevo dentista
                Dentista nuevoDentista = new Dentista(nombre, dni, telefono, 0, nColegiado, especialidad, fechaNacimiento, edad, null);
                dentistaDAO.insert(nuevoDentista);
                logger.info("Dentista añadido correctamente.");
            }

            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Éxito");
            alerta.setHeaderText("Dentista guardado");
            alerta.setContentText("El dentista se ha guardado correctamente.");
            alerta.showAndWait();

            if (dentistaController != null) {
                dentistaController.cargarDentistas(); // Actualizar la lista
            }

            cerrarVentana(event);
        }catch (DNIErroneoException e){
            logger.log(Level.SEVERE, "Error al guardar el dentista: " + e.getMessage(), e);
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("DNI inválido");
            alerta.setContentText(e.getMessage());
            alerta.showAndWait();
        }
        catch (TelefonoErroneoException e){
            logger.log(Level.SEVERE, "Error al guardar el dentista: " + e.getMessage(), e);
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Teléfono inválido");
            alerta.setContentText(e.getMessage());
            alerta.showAndWait();
        }
        catch (NColegiadoErroneoException e){
            logger.log(Level.SEVERE, "Error al guardar el dentista: " + e.getMessage(), e);
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Número de colegiado inválido");
            alerta.setContentText(e.getMessage());
            alerta.showAndWait();
        }
        catch (FechaNVaciaException e){
            logger.log(Level.SEVERE, "Error al guardar el dentista: " + e.getMessage(), e);
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Fecha de nacimiento vacía");
            alerta.setContentText(e.getMessage());
            alerta.showAndWait();
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, "Error al guardar el dentista: " + e.getMessage(), e);
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Error al añadir el dentista");
            alerta.setContentText("Por favor, verifica los datos ingresados.");
            alerta.showAndWait();
        }
    }

    /**
     * Cancela la operación y cierra la ventana.
     *
     * @param event realiza el cierre.
     */
    @FXML
    private void cancelar(ActionEvent event) {
        logger.info("Cancelando la operación y cerrando la ventana.");
        cerrarVentana(event);
    }

    /**
     * Cierra la ventana del formulario.
     *
     * @param event realiza el cierre.
     */
    private void cerrarVentana(ActionEvent event) {
        logger.info("Cerrando la ventana del formulario de dentista.");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private Dentista dentistaActual;

    /**
     * Carga los datos de un dentista en el formulario para su edición.
     *
     * @param dentista Dentista cuyos datos se cargarán.
     */
    public void cargarDatosDentista(Dentista dentista) {
        this.dentistaActual = dentista;
        nombreField.setText(dentista.getNombre());
        dniField.setText(dentista.getDni());
        telefonoField.setText(String.valueOf(dentista.getTelefono()));
        nColegiadoField.setText(dentista.getnColegiado());
        especialidadField.setText(dentista.getEspecialidad());
        fechaNacimientoPicker.setValue(dentista.getFechaNacimiento());
    }

}