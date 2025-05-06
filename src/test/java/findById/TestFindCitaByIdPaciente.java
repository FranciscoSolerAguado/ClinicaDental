package findById;

import DAO.CitaDAO;
import model.Cita;

import java.util.List;

public class TestFindCitaByIdPaciente {
    public static void main(String[] args) {
        int idPaciente = 5;
        List<Cita> citas = CitaDAO.findCitasByIdPacienteEager(idPaciente);

        if (citas.isEmpty()) {
            System.out.println("No se encontraron citas para el paciente con ID" + idPaciente);
        } else {
            for (Cita cita : citas) {
                System.out.println("Cita: " + cita);
            }
        }
    }
}
