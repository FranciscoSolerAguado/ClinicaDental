import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("inicio.fxml"));
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
        TratamientoDAO tratamientoDAO = TratamientoDAO.getInstance();
        TratamientoPacienteDAO tratamientoPacienteDAO = TratamientoPacienteDAO.getInstance();
        PacienteDAO pacienteDAO = PacienteDAO.getInstance();

        pacienteDAO.initialize(tratamientoDAO, tratamientoPacienteDAO);

        launch(args); // Lanza la aplicación JavaFX
    }
}
