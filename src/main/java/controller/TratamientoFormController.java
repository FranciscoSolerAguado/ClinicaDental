package controller;

import DAO.DentistaDAO;
import DAO.TratamientoDAO;
import exceptions.*;
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
    public void initialize() {
        cargarDentistas();

        dentistaComboBox.setConverter(new javafx.util.StringConverter<>() {
            @Override
            public String toString(Dentista dentista) {
                return dentista != null ? dentista.mostrarNombre() : "";
            }

            @Override
            public Dentista fromString(String string) {
                return dentistaComboBox.getItems().stream()
                        .filter(dentista -> dentista.mostrarNombre().equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });
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
                throw new DescripcionVaciaException("La descripción no puede estar vacía.");
            }

            double precio = Double.parseDouble(precioField.getText());
            if (precio <= 0) {
                throw new PrecioNegativoException("El precio debe ser un número positivo.");
            }

            Dentista dentistaSeleccionado = dentistaComboBox.getValue();
            if (dentistaSeleccionado == null) {
                throw new DentistaNoSeleccionadoException("Debe seleccionarse un dentista.");
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
        }catch (DescripcionVaciaException e){
            logger.log(Level.SEVERE, "Error al guardar el tratamiento: " + e.getMessage(), e);
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Error al añadir el tratamiento");
            alerta.setContentText("La descripción no puede estar vacía.");
            alerta.showAndWait();
        }
        catch (DentistaNoSeleccionadoException e){
            logger.log(Level.SEVERE, "Error al guardar el tratamiento: " + e.getMessage(), e);
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Error al añadir el tratamiento");
            alerta.setContentText("Debe seleccionarse un dentista.");
            alerta.showAndWait();
        }
        catch (IllegalArgumentException e) {
            logger.log(Level.SEVERE, "Error al guardar el tratamiento: " + e.getMessage(), e);
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Error al añadir el tratamiento");
            alerta.setContentText("La descripción no puede estar vacía.");
            alerta.showAndWait();
        }
        catch (PrecioNegativoException e){
            logger.log(Level.SEVERE, "Error al guardar el tratamiento: " + e.getMessage(), e);
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Error al añadir el tratamiento");
            alerta.setContentText("El precio debe ser un número positivo.");
            alerta.showAndWait();
        }
        catch (Exception e) {
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
