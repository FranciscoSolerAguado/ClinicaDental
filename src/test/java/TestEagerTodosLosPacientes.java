import DAO.PacienteDAO;
import model.Paciente;

import java.util.List;

public class TestEagerTodosLosPacientes {
    public static void main(String[] args) {
        List<Paciente> pacientes = PacienteDAO.findAllEager();

        for (Paciente paciente : pacientes){
            System.out.println(paciente);
        }
    }
}
