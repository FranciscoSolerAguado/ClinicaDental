package insert;

import DAO.DentistaDAO;
import model.Dentista;

public class TestInsertDentista {
    public static void main(String[] args) {
        // Crear un objeto Dentista con datos de prueba
        Dentista dentista = new Dentista();
        dentista.setNombre("Juan Perez Garcia");
        dentista.setDni("87654321A");
        dentista.setTelefono(621541321);
        dentista.setFechaNacimiento("1991-01-01");
        dentista.setEdad(33);

        // Insertar el dentista en la base de datos
        try {
            DentistaDAO.insert(dentista);
            System.out.println("Dentista insertado correctamente.");
        } catch (RuntimeException e) {
            System.err.println("Error al insertar el dentista: " + e.getMessage());
        }
    }
}
