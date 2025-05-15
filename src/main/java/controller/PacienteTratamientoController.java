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

    public void setPaciente(Paciente paciente) {
        this.pacienteActual = paciente;
        cargarTratamientos();
    }


    private void cargarTratamientos() {
        if (pacienteActual == null) {
            logger.warning("No se ha proporcionado un paciente.");
            return;
        }

        try {
            List<TratamientoPaciente> tratamientos = pacienteActual.getTratamientosPaciente();
            logger.info("Tratamientos recibidos: " + tratamientos.size());
            if (tratamientos == null || tratamientos.isEmpty()) {
                tratamientosListView.getItems().add("El paciente no tiene tratamientos asociados.");
            } else {
                tratamientos.forEach(tratamiento -> {
                    logger.info("Tratamiento: " + tratamiento.getDetalles());
                    tratamientosListView.getItems().add(tratamiento.getDetalles());
                });
            }
        } catch (Exception e) {
            logger.severe("Error al cargar los tratamientos: " + e.getMessage());
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Error al cargar los tratamientos");
            alerta.setContentText("No se pudieron cargar los tratamientos del paciente.");
            alerta.showAndWait();
        }
    }

    @FXML
    private void cerrarVentana() {
        Stage stage = (Stage) tratamientosListView.getScene().getWindow();
        stage.close();
    }
}
