package findAll;

import DAO.PacienteDAO;
import model.Paciente;

import java.util.List;

public class TestLazyTodosLosPacientes {
    public static void main(String[] args) {
        List<Paciente> pacientes = PacienteDAO.findAll();

        for (Paciente paciente : pacientes){
            System.out.println(paciente);
        }
    }
}
