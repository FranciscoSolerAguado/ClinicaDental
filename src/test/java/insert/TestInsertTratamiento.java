package insert;

import DAO.TratamientoDAO;
import model.Tratamiento;

public class TestInsertTratamiento {
    public static void main(String[] args) {
        TratamientoDAO tratamientoDAO = TratamientoDAO.getInstance();

        // Crear un objeto Tratamiento con datos de prueba
        Tratamiento tratamiento = new Tratamiento();
        tratamiento.setDescripcion("Blanqueamiento dental avanzado");
        tratamiento.setPrecio(120.0);
        tratamiento.setIdDentista(16);

        // Insertar el tratamiento en la base de datos
        try {
            tratamientoDAO.insert(tratamiento);
            System.out.println("Tratamiento insertado correctamente.");
        } catch (RuntimeException e) {
            System.err.println("Error al insertar el tratamiento: " + e.getMessage());
        }
    }
}
