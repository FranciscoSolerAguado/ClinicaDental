package findAll;

import DAO.TratamientoPacienteDAO;
import model.TratamientoPaciente;

import java.util.List;

public class TestTodosLosTratamientosPacientes {
    public static void main(String[] args) {
         List<TratamientoPaciente> tratamientos = TratamientoPacienteDAO.findAll();
         for (TratamientoPaciente t : tratamientos) {
             System.out.println(t);
        }
    }
}
