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
    @FXML
    private TextField edadField;

    private final DentistaDAO dentistaDAO = DentistaDAO.getInstance();
    private DentistaController dentistaController;
    private final static Logger logger = Logger.getLogger(DentistaFormController.class.getName());

    public void setDentistaController(DentistaController dentistaController) {
        this.dentistaController = dentistaController;
    }

    @FXML
    private void guardarDentista(ActionEvent event) {
        logger.info("Iniciando el proceso de guardar un nuevo dentista...");
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

            // Validar el número de colegiado
            String nColegiado = nColegiadoField.getText();
            logger.fine("Validando número de colegiado: " + nColegiado);
            if (!nColegiado.matches("^[0-9]{5}-[A-Z]$")) {
                logger.warning("Número de colegiado inválido: " + nColegiado);
                throw new NColegiadoErroneoException("Número de colegiado incorrecto. Debe ser 5 dígitos seguidos de '-' y una letra mayúscula.");
            }

            String especialidad = especialidadField.getText();
            logger.fine("Especialidad ingresada: " + especialidad);

            LocalDate fechaNacimiento = fechaNacimientoPicker.getValue();
            logger.fine("Fecha de nacimiento seleccionada: " + fechaNacimiento);

            // Calcular la edad automáticamente
            if (fechaNacimiento == null) {
                logger.warning("La fecha de nacimiento no puede estar vacía.");
                throw new FechaNVaciaException("La fecha de nacimiento no puede estar vacía.");
            }
            int edad = Period.between(fechaNacimiento, LocalDate.now()).getYears();
            logger.fine("Edad calculada: " + edad);

            Dentista nuevoDentista = new Dentista(nombre, dni, telefono, 0, nColegiado, especialidad, fechaNacimiento, edad, null);
            logger.info("Insertando el nuevo dentista en la base de datos: " + nuevoDentista);
            dentistaDAO.insert(nuevoDentista);

            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Éxito");
            alerta.setHeaderText("Dentista añadido");
            alerta.setContentText("El dentista se ha añadido correctamente.");
            alerta.showAndWait();

            logger.info("Dentista añadido correctamente. Actualizando la lista de dentistas...");
            if (dentistaController != null) {
                dentistaController.cargarDentistas();
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

    @FXML
    private void cancelar(ActionEvent event) {
        logger.info("Cancelando la operación y cerrando la ventana.");
        cerrarVentana(event);
    }

    private void cerrarVentana(ActionEvent event) {
        logger.info("Cerrando la ventana del formulario de dentista.");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}