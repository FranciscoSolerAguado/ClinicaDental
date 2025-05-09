package insert;

import DAO.DentistaDAO;
import model.Dentista;

import java.time.LocalDate;

public class TestInsertDentista {
    public static void main(String[] args) {
        DentistaDAO dentistaDAO = DentistaDAO.getInstance();

        // Crear un objeto Dentista con datos de prueba
        Dentista dentista = new Dentista();
        dentista.setNombre("Ana María López");
        dentista.setDni("12345678Z");
        dentista.setTelefono(612345678);
        dentista.setnColegiado("12345-X");
        dentista.setEspecialidad("Implantología y cirugía oral");
        dentista.setFechaNacimiento(LocalDate.of(1985, 5, 15));
        dentista.setEdad(38);

        // Insertar el dentista en la base de datos
        try {
            dentistaDAO.insert(dentista);
            System.out.println("Dentista insertado correctamente.");
        } catch (RuntimeException e) {
            System.err.println("Error al insertar el dentista: " + e.getMessage());
        }
    }
}
