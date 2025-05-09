package update;

import DAO.PacienteDAO;

public class TestUpdatePaciente {
    public static void main(String[] args) {
        PacienteDAO pacienteDAO = PacienteDAO.getInstance();

        int idPaciente = 24;
        String dniNuevo = "23956923A";

        try{
            pacienteDAO.updateDni(idPaciente, dniNuevo);
            System.out.println("DNI actualizado correctamente.");
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
