package findByName;

import DAO.CitaDAO;
import model.Cita;

import java.util.List;

public class TestfindByNameCita {
    public static void main(String[] args) {
        List<Cita> citas = CitaDAO.findByNameEager("Alba Vidal López");

        if (citas.isEmpty()) {
            System.out.println("No se encontraron citas para el paciente con nombre 'Alba Vidal López'.");
        } else {
            for (Cita cita : citas) {
                System.out.println("Cita: " + cita);
            }
        }
    }
}
