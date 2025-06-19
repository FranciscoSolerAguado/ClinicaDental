package controller;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.util.Duration;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class InicioController {

    @FXML
    private AnchorPane rootPane;

    /**
     * Método que se ejecuta al inicializar la vista.
     * Configura una pantalla de caraga de 3 segundos antes de cambiar a la vista principal.
     */
    @FXML
    public void initialize() { //PANTALLA DE CARGA
        // Espera 3 segundos antes de cambiar a pantalla completa
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(event -> ponerPantallaCompleta());
        pause.play();
    }

    /**
     * Cambia la vista actual a la vista principal y ajusta la ventana.
     */
    private void ponerPantallaCompleta() {
        try {
            // Cargar el archivo main.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../main.fxml"));
            AnchorPane mainPane = loader.load();

            // Obtener la escena desde el rootPane
            Scene scene = rootPane.getScene();
            scene.setRoot(mainPane);

            // Obtener el Stage actual
            Stage stage = (Stage) scene.getWindow();

            // Evitar modo maximizado y pantalla completa
            stage.setFullScreen(false);
            stage.setMaximized(false);

            // Obtener el área visible sin la barra de tareas
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

            // Establecer el tamaño del stage al 100% de esa área visible
            stage.setX(screenBounds.getMinX());
            stage.setY(screenBounds.getMinY());
            stage.setWidth(screenBounds.getWidth());
            stage.setHeight(screenBounds.getHeight());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}