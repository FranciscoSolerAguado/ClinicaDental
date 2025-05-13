package controller;

import DAO.DentistaDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.Dentista;

import java.io.IOException;
import java.util.List;

public class DentistaController {

    @FXML
    private ListView<String> dentistaListView;
    private final DentistaDAO dentistaDAO = DentistaDAO.getInstance();

    @FXML
    public void initialize() {
        // Cargar los nombres de los dentistas en el ListView
        dentistaDAO.findAll().forEach(dentista -> {
            if (dentista instanceof Dentista) {
                dentistaListView.getItems().add(((Dentista) dentista).getNombre());
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

