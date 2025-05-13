package controller;

import DAO.TratamientoDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.Tratamiento;

import java.io.IOException;

public class TratamientoController {
    @FXML
    private ListView<String> tratamientoListView;
    private final TratamientoDAO tratamientoDAO = TratamientoDAO.getInstance();

    @FXML
    public void initialize() {
        // Cargar los nombres de los tratamientos en el ListView
        tratamientoDAO.findAll().forEach(tratamiento -> {
            if (tratamiento instanceof Tratamiento) {
                tratamientoListView.getItems().add(((Tratamiento) tratamiento).getDescripcion());
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
