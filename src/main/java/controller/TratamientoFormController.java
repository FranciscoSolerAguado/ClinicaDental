package controller;

import DAO.DentistaDAO;
import DAO.TratamientoDAO;
import exceptions.DNIErroneoException;
import exceptions.NColegiadoErroneoException;
import exceptions.TelefonoErroneoException;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import model.Dentista;
import model.Tratamiento;
import model.Tratamiento;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TratamientoFormController {
    @FXML
    private TextField descripcionField;
    @FXML
    private TextField precioField;
    @FXML
    private ComboBox<Dentista> dentistaComboBox;

    private final TratamientoDAO tratamientoDAO = TratamientoDAO.getInstance();
    private TratamientoController tratamientoController;
    private final static Logger logger = Logger.getLogger(TratamientoFormController.class.getName());

    public void setTratamientoController(TratamientoController tratamientoController) {
        this.tratamientoController = tratamientoController;
    }

    @FXML
    public void initialize(){
        cargarDentistas();
    }

    private void cargarDentistas() {
        try {
            // Obtén la lista de dentistas desde la base de datos
            List<Object> dentistas = DentistaDAO.getInstance().findAll();
            for (Object obj : dentistas) {
                if (obj instanceof Dentista) {
                    dentistaComboBox.getItems().add((Dentista) obj);
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al cargar los dentistas: " + e.getMessage(), e);
        }
    }

    @FXML
    private void guardarTratamiento(ActionEvent event) {
        logger.info("Iniciando el proceso de guardar un nuevo tratamiento...");
        try {
            String descripcion = descripcionField.getText();
            if (descripcion == null || descripcion.isEmpty()) {
                throw new IllegalArgumentException("La descripción no puede estar vacía.");
            }

            String precioStr = precioField.getText();
            if (!precioStr.matches("^\\d+(\\.\\d{1,2})?$")) {
                throw new IllegalArgumentException("El precio debe ser un número válido con hasta dos decimales.");
            }
            double precio = Double.parseDouble(precioStr);

            Dentista dentistaSeleccionado = dentistaComboBox.getValue();
            if (dentistaSeleccionado == null) {
                throw new IllegalArgumentException("Debe seleccionarse un dentista.");
            }

            // Crear el tratamiento con el id del dentista seleccionado
            Tratamiento nuevoTratamiento = new Tratamiento(descripcion, precio, dentistaSeleccionado.getIdDentista());
            tratamientoDAO.insert(nuevoTratamiento);

            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Éxito");
            alerta.setHeaderText("Tratamiento añadido");
            alerta.setContentText("El tratamiento se ha añadido correctamente.");
            alerta.showAndWait();

            if (tratamientoController != null) {
                tratamientoController.cargarTratamientos();
            }

            cerrarVentana(event);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al guardar el tratamiento: " + e.getMessage(), e);
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Error al añadir el tratamiento");
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
        logger.info("Cerrando la ventana del formulario de tratamiento.");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
