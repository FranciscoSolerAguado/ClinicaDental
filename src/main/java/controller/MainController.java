package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController {
    @FXML
    private HBox topBar;

    @FXML
    private AnchorPane rootPane;


    private static final Logger logger = Logger.getLogger(MainController.class.getName());

    private Stage stage;
    private Scene scene;

    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private void initialize() {
        topBar.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        topBar.setOnMouseDragged(event -> {
            Stage stage = (Stage) topBar.getScene().getWindow();
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }

    /**
     * Cambia a la vista de Dentista.
     *
     * @param event realiza el cambio de vista.
     * @throws IOException Si ocurre un error al cargar el archivo FXML.
     */
    @FXML
    private void switchToDentista(ActionEvent event) throws IOException {
        logger.info("Cambiando a la vista de Dentista...");
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/dentista.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);

            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds(); // Obtiene el tamaño de la pantalla
            stage.setX(screenBounds.getMinX()); // Establece la posición X de la ventana
            stage.setY(screenBounds.getMinY()); // Establece la posición Y de la ventana
            stage.setWidth(screenBounds.getWidth()); // Establece el ancho de la ventana
            stage.setHeight(screenBounds.getHeight()); // Establece la altura de la ventana

            stage.show();
            logger.info("Vista de Dentista cargada correctamente.");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error al cargar la vista de Dentista.", e);
            throw e;
        }
    }

    /**
     * Cambia a la vista de Paciente.
     *
     * @param event realiza el cambio de vista.
     * @throws IOException Si ocurre un error al cargar el archivo FXML.
     */
    @FXML
    private void switchToPaciente(ActionEvent event) throws IOException {
        logger.info("Cambiando a la vista de Paciente...");
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/paciente.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);

            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX(screenBounds.getMinX()); // Establece la posición X de la ventana
            stage.setY(screenBounds.getMinY()); // Establece la posición Y de la ventana
            stage.setWidth(screenBounds.getWidth()); // Establece el ancho de la ventana
            stage.setHeight(screenBounds.getHeight()); // Establece la altura de la ventana

            stage.show();
            logger.info("Vista de Paciente cargada correctamente.");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error al cargar la vista de Paciente.", e);
            throw e;
        }
    }

    /**
     * Cambia a la vista de Tratamiento.
     *
     * @param event realiza el cambio de vista.
     * @throws IOException Si ocurre un error al cargar el archivo FXML.
     */
    @FXML
    private void switchToTratamiento(ActionEvent event) throws IOException {
        logger.info("Cambiando a la vista de Tratamiento...");
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/tratamiento.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);

            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX(screenBounds.getMinX()); // Establece la posición X de la ventana
            stage.setY(screenBounds.getMinY()); // Establece la posición Y de la ventana
            stage.setWidth(screenBounds.getWidth()); // Establece el ancho de la ventana
            stage.setHeight(screenBounds.getHeight()); // Establece la altura de la ventana

            stage.show();
            logger.info("Vista de Tratamiento cargada correctamente.");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error al cargar la vista de Tratamiento.", e);
            throw e;
        }
    }

    /**
     * Cambia a la vista de Tratamientos a Pacientes.
     *
     * @param event realiza el cambio de vista.
     * @throws IOException Si ocurre un error al cargar el archivo FXML.
     */
    @FXML
    private void switchToTratamientosPacientes(ActionEvent event) throws IOException {
        logger.info("Cambiando a la vista de Tratamientos a Pacientes...");
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/tratamientosPacientes.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);

            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX(screenBounds.getMinX()); // Establece la posición X de la ventana
            stage.setY(screenBounds.getMinY()); // Establece la posición Y de la ventana
            stage.setWidth(screenBounds.getWidth()); // Establece el ancho de la ventana
            stage.setHeight(screenBounds.getHeight()); // Establece la altura de la ventana

            stage.show();
            logger.info("Vista de Tratamientos a Pacientes cargada correctamente.");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error al cargar la vista de Tratamientos a Pacientes.", e);
            throw e;
        }
    }


    @FXML
    private void handleMinimize() {
        Stage stage = (Stage) rootPane.getScene().getWindow(); // rootPane es tu AnchorPane con fx:id
        stage.setIconified(true);
    }

    @FXML
    private void handleToggleMaximize() {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        if (stage.isMaximized()) {
            stage.setMaximized(false);
            stage.setWidth(1200);
            stage.setHeight(800);
            stage.centerOnScreen();
        } else {
            stage.setMaximized(true);
        }
    }

    @FXML
    private void handleClose() {
        Platform.exit();
    }

}