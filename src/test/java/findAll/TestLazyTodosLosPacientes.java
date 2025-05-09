package findAll;

import DAO.PacienteDAO;
import model.Paciente;

import java.util.List;

public class TestLazyTodosLosPacientes {
    public static void main(String[] args) {
        PacienteDAO pacienteDAO = PacienteDAO.getInstance();
        List<Paciente> pacientes = pacienteDAO.findAll();

        for (Paciente paciente : pacientes){
            System.out.println(paciente);
        }
    }
}
