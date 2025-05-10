package findById;

import DAO.TratamientoPacienteDAO;
import model.TratamientoPaciente;

public class TestFindByIDTratamientoPaciente {
    public static void main(String[] args) {
        TratamientoPacienteDAO tratamientoPacienteDAO = TratamientoPacienteDAO.getInstance();
        int id = 1;
        TratamientoPaciente tratamientoPaciente = tratamientoPacienteDAO.findById(id);
        if (tratamientoPaciente != null) {
            System.out.println("TratamientoPaciente encontrado: " + tratamientoPaciente);
        } else {
            System.out.println("TratamientoPaciente no encontrado con ID: " + id);
        }
    }
}
