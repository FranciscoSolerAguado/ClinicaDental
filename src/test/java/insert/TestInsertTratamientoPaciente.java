package insert;

import DAO.TratamientoPacienteDAO;

import java.time.LocalDate;

public class TestInsertTratamientoPaciente {
    public static void main(String[] args) {
        TratamientoPacienteDAO tratamientoPacienteDAO = TratamientoPacienteDAO.getInstance();

        int idTratamiento = 14;
        int idPaciente = 24;
        LocalDate fechaTratamientoNueva = LocalDate.now();
        String detallesNuevos = "Blanqueamiento dental avanzado con láser, buenos resultados";

        tratamientoPacienteDAO.insert(idPaciente, idTratamiento, String.valueOf(fechaTratamientoNueva), detallesNuevos);

        System.out.println("Inserción exitosa en la tabla tratamientopaciente");
    }
}
