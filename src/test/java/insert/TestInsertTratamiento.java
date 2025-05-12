package insert;

import DAO.TratamientoDAO;
import model.Tratamiento;
import utils.LoggerUtil;

import java.util.logging.Logger;

public class TestInsertTratamiento {
    public static void main(String[] args) {
        Logger logger = LoggerUtil.getLogger();
        TratamientoDAO tratamientoDAO = TratamientoDAO.getInstance();

        logger.info("🧪 Test de inserción de tratamiento iniciado");

        // Crear un objeto Tratamiento con datos de prueba
        Tratamiento tratamiento = new Tratamiento();
        tratamiento.setDescripcion("Blanqueamiento dental avanzado");
        tratamiento.setPrecio(120.0);
        tratamiento.setIdDentista(16);




        // Insertar el tratamiento en la base de datos
        try {
            tratamientoDAO.insert(tratamiento);
            logger.info("✅ Tratamiento insertado correctamente.");
            System.out.println("Tratamiento insertado correctamente.");
        } catch (RuntimeException e) {
            logger.severe("❌ Error durante la inserción del tratamiento: " + e.getMessage());
            System.err.println("Error al insertar el tratamiento: " + e.getMessage());
        }
    }
}
