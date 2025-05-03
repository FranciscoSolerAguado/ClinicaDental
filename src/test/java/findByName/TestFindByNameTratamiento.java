package findByName;

import DAO.TratamientoDAO;
import model.Tratamiento;

import java.util.List;

public class TestFindByNameTratamiento {
    public static void main(String[] args) {
        List<Tratamiento> tratamientos = TratamientoDAO.findByNameEager("Alba Vidal López");

        if (tratamientos.isEmpty()) {
            System.out.println("No se encontraron tratamientos para el paciente con nombre 'Alba Vidal López'.");
        } else {
            for (Tratamiento tratamiento : tratamientos) {
                System.out.println("Tratamiento: " + tratamiento);
            }
        }
    }
}
