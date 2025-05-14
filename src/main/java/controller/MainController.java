package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController {

    private static final Logger logger = Logger.getLogger(MainController.class.getName());

    private Stage stage;
    private Scene scene;

    @FXML
    private void switchToDentista(ActionEvent event) throws IOException {
        logger.info("Cambiando a la vista de Dentista...");
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/dentista.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);

            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX(screenBounds.getMinX());
            stage.setY(screenBounds.getMinY());
            stage.setWidth(screenBounds.getWidth());
            stage.setHeight(screenBounds.getHeight());

            stage.show();
            logger.info("Vista de Dentista cargada correctamente.");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error al cargar la vista de Dentista.", e);
            throw e;
        }
    }

    @FXML
    private void switchToPaciente(ActionEvent event) throws IOException {
        logger.info("Cambiando a la vista de Paciente...");
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/paciente.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);

            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX(screenBounds.getMinX());
            stage.setY(screenBounds.getMinY());
            stage.setWidth(screenBounds.getWidth());
            stage.setHeight(screenBounds.getHeight());

            stage.show();
            logger.info("Vista de Paciente cargada correctamente.");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error al cargar la vista de Paciente.", e);
            throw e;
        }
    }

    @FXML
    private void switchToTratamiento(ActionEvent event) throws IOException {
        logger.info("Cambiando a la vista de Tratamiento...");
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/tratamiento.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);

            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX(screenBounds.getMinX());
            stage.setY(screenBounds.getMinY());
            stage.setWidth(screenBounds.getWidth());
            stage.setHeight(screenBounds.getHeight());

            stage.show();
            logger.info("Vista de Tratamiento cargada correctamente.");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error al cargar la vista de Tratamiento.", e);
            throw e;
        }
    }

    @FXML
    private void switchToTratamientosPacientes(ActionEvent event) throws IOException {
        logger.info("Cambiando a la vista de Tratamientos a Pacientes...");
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/tratamientosPacientes.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);

            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX(screenBounds.getMinX());
            stage.setY(screenBounds.getMinY());
            stage.setWidth(screenBounds.getWidth());
            stage.setHeight(screenBounds.getHeight());

            stage.show();
            logger.info("Vista de Tratamientos a Pacientes cargada correctamente.");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error al cargar la vista de Tratamientos a Pacientes.", e);
            throw e;
        }
    }
}