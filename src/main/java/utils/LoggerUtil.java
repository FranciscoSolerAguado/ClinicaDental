package utils;

import java.io.IOException;
import java.util.logging.*;

public class LoggerUtil {
    private static Logger logger;

    static {
        try {
            LogManager.getLogManager().reset();
            logger = Logger.getLogger("ClinicaLogger");
            logger.setLevel(Level.ALL);

            FileHandler fileHandler = new FileHandler("clinica.log", true);
            fileHandler.setLevel(Level.ALL);
            fileHandler.setFormatter(new SimpleFormatter());

            logger.addHandler(fileHandler);
        } catch (IOException e) {
            System.err.println("❌ No se pudo inicializar el logger: " + e.getMessage());
        }
    }

    public static Logger getLogger() {
        return logger;
    }
}
