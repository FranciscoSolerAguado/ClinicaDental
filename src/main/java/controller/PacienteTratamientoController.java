package controller;

import DAO.PacienteDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.Paciente;
import model.TratamientoPaciente;

import java.util.List;
import java.util.logging.Logger;

public class PacienteTratamientoController {

    @FXML
    private ListView<String> tratamientosListView;

    private static final Logger logger = Logger.getLogger(PacienteTratamientoController.class.getName());
    private final PacienteDAO pacienteDAO = PacienteDAO.getInstance();
    private Paciente pacienteActual;

    /**
     * Establece el paciente actual y carga sus tratamientos.
     * @param paciente Paciente cuyos tratamientos se mostrarán.
     */
    public void setPaciente(Paciente paciente) {
        this.pacienteActual = paciente;
        cargarTratamientos();
    }


    /**
     * Carga los tratamientos asociados al paciente actual y los muestra en el ListView.
     */
    private void cargarTratamientos() {
        if (pacienteActual == null) {
            logger.warning("No se ha proporcionado un paciente."); // Advertencia si no se ha establecido un paciente.
            return;
        }

        try {
            // Obtener la lista de tratamientos del paciente.
            List<TratamientoPaciente> tratamientos = pacienteActual.getTratamientosPaciente();
            logger.info("Tratamientos recibidos: " + tratamientos.size()); // Registrar la cantidad de tratamientos.

            if (tratamientos == null || tratamientos.isEmpty()) {
                // Si no hay tratamientos, mostrar un mensaje en el ListView.
                tratamientosListView.getItems().add("El paciente no tiene tratamientos asociados.");
            } else {
                // Agregar los detalles de cada tratamiento al ListView.
                tratamientos.forEach(tratamiento -> {
                    logger.info("Tratamiento: " + tratamiento.getDetalles());
                    tratamientosListView.getItems().add(tratamiento.getDetalles());
                });
            }
        } catch (Exception e) {
            // Manejo de errores al cargar los tratamientos.
            logger.severe("Error al cargar los tratamientos: " + e.getMessage());
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Error al cargar los tratamientos");
            alerta.setContentText("No se pudieron cargar los tratamientos del paciente.");
            alerta.showAndWait();
        }
    }

    /**
     * Cierra la ventana actual.
     */
    @FXML
    private void cerrarVentana() {
        Stage stage = (Stage) tratamientosListView.getScene().getWindow();
        stage.close();
    }
}
