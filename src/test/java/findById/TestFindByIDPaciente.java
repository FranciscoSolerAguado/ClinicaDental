package findById;

import DAO.PacienteDAO;
import model.Paciente;

public class TestFindByIDPaciente {
    public static void main(String[] args) {

        int id = 1;

        Paciente paciente = PacienteDAO.findByIdEager(id);

        if (paciente != null) {
            System.out.println("Paciente encontrado: " + paciente);
        } else {
            System.out.println("Paciente no encontrado con ID: " + id);
        }
    }
}
