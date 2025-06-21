package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Logger;

public class MainController {
    @FXML
    private HBox topBar;

    @FXML
    private AnchorPane rootPane;

    //declaracion del centerContent que permite cargar el contenido de las distintas pantallas en la principal dentro del splitPane
    @FXML
    private VBox centerContent;


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
     * Método para cambiar el contenido del área central (centerContent) de la ventana principal.
     */
    private void loadContent(String fxmlPath) {
        try {
            Node content = FXMLLoader.load(getClass().getResource(fxmlPath));
            centerContent.getChildren().setAll(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * carga el contenido de pacientes.fxml en el VBox centerContent (`splitPane` en la vista principal)
     */
    @FXML
    private void switchToPacientes() {
        loadContent("/fxml/pacientes.fxml");
    }

    /**
     * carga el contenido de dentistas.fxml en el VBox centerContent (`splitPane` en la vista principal)
     */
    @FXML
    private void switchToDentistas() {
        loadContent("/fxml/dentistas.fxml");
    }

    /**
     * carga el contenido de tratamientos.fxml en el VBox centerContent (`splitPane` en la vista principal)
     */
    @FXML
    private void switchToTratamientos() {
        loadContent("/fxml/tratamientos.fxml");
    }

    /**
     * carga el contenido de tratamientosPacientes.fxml en el VBox centerContent (`splitPane` en la vista principal)
     */
    @FXML
    private void switchToTratamientosPacientes() {
        loadContent("/fxml/tratamientosPacientes.fxml");
    }

    /**
     * Método que maneja el evento de minimizar la ventana
     */
    @FXML
    private void handleMinimize() {
        Stage stage = (Stage) rootPane.getScene().getWindow(); // rootPane es tu AnchorPane con fx:id
        stage.setIconified(true);
    }

    /**
     * Método que maneja el evento de maximizar o restaurar la ventana.
     */
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

    /**
     * Método que maneja el evento de cerrar la aplicación.
     */
    @FXML
    private void handleClose() {
        Platform.exit();
    }

}