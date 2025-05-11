package findAll;

import DAO.PacienteDAO;
import model.Paciente;

import java.util.List;

public class TestEagerTodosLosPacientes {
    public static void main(String[] args) {
        PacienteDAO pacienteDAO = PacienteDAO.getInstance();
        List<Object> pacientes = pacienteDAO.findAllEager();

        for (Object paciente : pacientes){
            System.out.println(paciente);
        }
    }
}
