package insert;

import DAO.DentistaDAO;
import model.Dentista;
import utils.LoggerUtil;
import utils.Utilidades;

import java.time.LocalDate;
import java.util.logging.Logger;

public class TestInsertDentista {
    public static void main(String[] args) {
        DentistaDAO dentistaDAO = DentistaDAO.getInstance();

        String dni = Utilidades.pideDNI("Introduce el DNI del paciente: ");
        int telefono = Utilidades.pideTelefono("Introduce el teléfono del paciente: ");
        String nColegiado = Utilidades.pideNColegiado("Introduce el número de colegiado del dentista: ");

        Logger logger = LoggerUtil.getLogger();
        logger.info("🧪 Test de inserción iniciado");

        // Crear un objeto Dentista con datos de prueba
        Dentista dentista = new Dentista();
        dentista.setNombre("Carlos García Jiménez");
        dentista.setDni(dni);
        dentista.setTelefono(telefono);
        dentista.setnColegiado(nColegiado);
        dentista.setEspecialidad("Ortodoncia");
        dentista.setFechaNacimiento(LocalDate.of(2000, 1, 1));
        dentista.setEdad(25);

        // Insertar el dentista en la base de datos
        try {
            dentistaDAO.insert(dentista);
            logger.info("✅ Dentista insertado correctamente.");
            System.out.println("Dentista insertado correctamente.");
        } catch (RuntimeException e) {
            logger.severe("❌ Error durante la inserción: " + e.getMessage());
            System.err.println("Error al insertar el dentista: " + e.getMessage());
        }
    }
}
