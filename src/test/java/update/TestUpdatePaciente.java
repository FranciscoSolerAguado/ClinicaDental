package update;

import DAO.PacienteDAO;

public class TestUpdatePaciente {
    public static void main(String[] args) {
        int idPaciente = 23;
        String dniNuevo = "12345678C";

        try{
            PacienteDAO.updateDni(idPaciente, dniNuevo);
            System.out.println("DNI actualizado correctamente.");
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
