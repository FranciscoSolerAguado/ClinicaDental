package controller;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class InicioController {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label welcomeLabel;

    @FXML
    public void initialize() {
        // Espera 3 segundos antes de cambiar a pantalla completa
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(event -> ponerPantallaCompleta());
        pause.play();
    }

    private void ponerPantallaCompleta() {
        try {
            // Cargar el archivo main.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../main.fxml"));
            AnchorPane mainPane = loader.load();

            // Obtener la escena actual y establecer el nuevo contenido
            Scene scene = rootPane.getScene();
            scene.setRoot(mainPane);

            // Configurar la ventana en modo maximizado
            Stage stage = (Stage) scene.getWindow();
            stage.setFullScreen(false); // Desactivar pantalla completa
            stage.setMaximized(true);  // Maximizar la ventana
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}