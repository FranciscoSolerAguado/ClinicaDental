package insert;

import DAO.TratamientoDAO;
import model.TipoTratamiento;
import model.Tratamiento;

public class TestInsertTratamiento {
    public static void main(String[] args) {
        // Crear un objeto Tratamiento con datos de prueba
        Tratamiento tratamiento = new Tratamiento();
        tratamiento.setTipoTratamiento(TipoTratamiento.LIMPIEZA_DENTAL);
        tratamiento.setNombrePaciente("Jhon Doe");
        tratamiento.setDescripcion("Limpieza dental completa");
        tratamiento.setPrecio(50.0);

        // Insertar el tratamiento en la base de datos
        try {
            TratamientoDAO.insert(tratamiento);
            System.out.println("Tratamiento insertado correctamente.");
        } catch (RuntimeException e) {
            System.err.println("Error al insertar el tratamiento: " + e.getMessage());
        }
    }
}
