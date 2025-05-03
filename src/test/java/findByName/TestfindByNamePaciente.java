package findByName;

import DAO.DentistaDAO;
import DAO.PacienteDAO;
import model.Paciente;

public class TestfindByNamePaciente {
    public static void main(String[] args) {
        Paciente paciente = PacienteDAO.findByNameEager("Nuria Delgado Varela");

        if (paciente != null) {
            System.out.println(paciente);
        } else {
            System.out.println("Paciente no encontrado");
        }

    }
}
