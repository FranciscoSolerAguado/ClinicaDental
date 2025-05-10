package findByName;

import DAO.PacienteDAO;
import model.Paciente;

public class TestfindByNamePaciente {
    public static void main(String[] args) {
        PacienteDAO pacienteDAO = PacienteDAO.getInstance();

        Paciente paciente = pacienteDAO.findByNameEager("Nuria Delgado Varela");

        if (paciente != null) {
            System.out.println(paciente);
        } else {
            System.out.println("Paciente no encontrado");
        }

    }
}
