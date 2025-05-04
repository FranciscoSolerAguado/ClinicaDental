package insert;

public class TestInsertTratamientoPaciente {
    public static void main(String[] args) {

        int idTratamiento = 11;
        int idPaciente = 23;

        DAO.TratamientoPacienteDAO.insert(idTratamiento, idPaciente);

        System.out.println("Inserción exitosa en la tabla tratamientopaciente");
    }
}
