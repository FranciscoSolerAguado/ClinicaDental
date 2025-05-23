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

import java.util.List;
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
    private Tratamiento tratamientoActual;

    /**
     * Establece el controlador principal para actualizar la lista de tratamientos.
     *
     * @param tratamientoController Controlador principal.
     */
    public void setTratamientoController(TratamientoController tratamientoController) {
        this.tratamientoController = tratamientoController;
    }

    /**
     * Método que se ejecuta al inicializar la vista.
     * Carga los dentistas en el ComboBox y configura su conversor.
     */
    @FXML
    public void initialize() {
        // Carga los dentistas desde la base de datos y los añade al ComboBox.
        cargarDentistas();

        // Configura un conversor para el ComboBox que permite mostrar los nombres de los dentistas
        // asociados a ese ID.
        dentistaComboBox.setConverter(new javafx.util.StringConverter<>() {
            @Override
            public String toString(Dentista dentista) {
                // Devuelve el nombre del dentista si no es nulo, de lo contrario, devuelve una cadena vacía.
                return dentista != null ? dentista.mostrarNombre() : "";
            }

            @Override
            public Dentista fromString(String string) {
                // Busca en la lista de elementos del ComboBox un dentista cuyo nombre coincida con la cadena proporcionada.
                // Si no encuentra coincidencias, devuelve null.
                return dentistaComboBox.getItems().stream()
                        .filter(dentista -> dentista.mostrarNombre().equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });
    }


    /**
     * Carga los dentistas desde la base de datos y los añade al ComboBox.
     */
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


    /**
     * Método que se ejecuta al hacer clic en el botón "Guardar".
     * Valida los datos ingresados y guarda el tratamiento en la base de datos.
     *
     * @param event Evento de acción.
     */
    @FXML
    private void guardarTratamiento(ActionEvent event) {
        logger.info("Iniciando el proceso de guardar un tratamiento...");
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

            if (tratamientoActual != null) {
                // Editar tratamiento existente
                tratamientoActual.setDescripcion(descripcion);
                tratamientoActual.setPrecio(precio);
                tratamientoActual.setDentista(dentistaSeleccionado);

                tratamientoDAO.update(tratamientoActual.getIdTratamiento(), tratamientoActual);
                logger.info("Tratamiento actualizado correctamente.");
            } else {
                // Crear un nuevo tratamiento
                Tratamiento nuevoTratamiento = new Tratamiento(descripcion, precio, dentistaSeleccionado);
                tratamientoDAO.insert(nuevoTratamiento);
                logger.info("Tratamiento añadido correctamente.");
            }

            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Éxito");
            alerta.setHeaderText("Tratamiento guardado");
            alerta.setContentText("El tratamiento se ha guardado correctamente.");
            alerta.showAndWait();

            if (tratamientoController != null) {
                tratamientoController.cargarTratamientos(); // Actualizar la lista
            }

            cerrarVentana(event);
        } catch (DescripcionVaciaException e) {
            logger.log(Level.SEVERE, "Error al guardar el tratamiento: " + e.getMessage(), e);
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Error al añadir el tratamiento");
            alerta.setContentText("La descripción no puede estar vacía.");
            alerta.showAndWait();
        } catch (DentistaNoSeleccionadoException e) {
            logger.log(Level.SEVERE, "Error al guardar el tratamiento: " + e.getMessage(), e);
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Error al añadir el tratamiento");
            alerta.setContentText("Debe seleccionarse un dentista.");
            alerta.showAndWait();
        } catch (IllegalArgumentException e) {
            logger.log(Level.SEVERE, "Error al guardar el tratamiento: " + e.getMessage(), e);
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Error al añadir el tratamiento");
            alerta.setContentText("La descripción no puede estar vacía.");
            alerta.showAndWait();
        } catch (PrecioNegativoException e) {
            logger.log(Level.SEVERE, "Error al guardar el tratamiento: " + e.getMessage(), e);
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Error al añadir el tratamiento");
            alerta.setContentText("El precio debe ser un número positivo.");
            alerta.showAndWait();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al guardar el tratamiento: " + e.getMessage(), e);
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Error al añadir el tratamiento");
            alerta.setContentText("Por favor, verifica los datos ingresados.");
            alerta.showAndWait();
        }
    }

    /**
     * Método que se ejecuta al hacer clic en el botón "Cancelar".
     * Cierra la ventana del formulario.
     *
     * @param event Evento de acción.
     */
    @FXML
    private void cancelar(ActionEvent event) {
        logger.info("Cancelando la operación y cerrando la ventana.");
        cerrarVentana(event);
    }

    /**
     * Cierra la ventana actual.
     *
     * @param event Evento de acción.
     */
    private void cerrarVentana(ActionEvent event) {
        logger.info("Cerrando la ventana del formulario de tratamiento.");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * Carga los datos del tratamiento en el formulario para su edición.
     *
     * @param tratamiento Tratamiento a cargar.
     */
    public void cargarDatosTratamiento(Tratamiento tratamiento) {
        this.tratamientoActual = tratamiento;
        descripcionField.setText(tratamiento.getDescripcion());
        precioField.setText(String.valueOf(tratamiento.getPrecio()));
        dentistaComboBox.setValue(tratamiento.getDentista());
        dentistaComboBox.getItems().stream()
                .filter(dentista -> dentista.equals(tratamiento.getDentista()))
                .findFirst()
                .orElse(null);
    }
}
