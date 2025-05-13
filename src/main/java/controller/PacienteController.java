package controller;

import DAO.DentistaDAO;
import DAO.PacienteDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.Dentista;
import model.Paciente;

import java.io.IOException;

public class PacienteController {
    @FXML
    private ListView<String> pacienteListView;
    private final PacienteDAO pacienteDAO = PacienteDAO.getInstance();

    @FXML
    public void initialize() {
        // Cargar los nombres de los pacientes en el ListView
        pacienteDAO.findAll().forEach(paciente -> {
            if (paciente instanceof Paciente) {
                pacienteListView.getItems().add(((Paciente) paciente).getNombre());
            }
        });
    }

    @FXML
    private void volverAMain(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/main.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaximized(true); // Maximizar la ventana con bordes
        stage.show();
    }
}
