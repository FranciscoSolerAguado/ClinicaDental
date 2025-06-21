import DAO.PacienteDAO;
import DAO.TratamientoDAO;
import DAO.TratamientoPacienteDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Carga la fuente work sans
        Font.loadFont(getClass().getResourceAsStream("/fonts/WorkSans-Regular.ttf"), 12);

        // Quitar la decoración de la ventana
        stage.initStyle(StageStyle.UNDECORATED);

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/fxml/inicio.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Clinica Dental");
        stage.setScene(scene);
        stage.show();
    }

    private void initializeDAOs() {
        // Crear instancias de los DAOs
        TratamientoDAO tratamientoDAO = TratamientoDAO.getInstance();
        TratamientoPacienteDAO tratamientoPacienteDAO = TratamientoPacienteDAO.getInstance();
        PacienteDAO pacienteDAO = PacienteDAO.getInstance();

        // Configurar dependencias entre los DAOs
        pacienteDAO.initialize(tratamientoDAO, tratamientoPacienteDAO);
        tratamientoPacienteDAO.initialize(pacienteDAO);
    }

    public static void main(String[] args) {
        // Inicializar DAOs antes de lanzar la app
        TratamientoDAO tratamientoDAO = TratamientoDAO.getInstance();
        TratamientoPacienteDAO tratamientoPacienteDAO = TratamientoPacienteDAO.getInstance();
        PacienteDAO pacienteDAO = PacienteDAO.getInstance();

        pacienteDAO.initialize(tratamientoDAO, tratamientoPacienteDAO);

        launch(args); // Lanza la aplicación JavaFX
    }
}

