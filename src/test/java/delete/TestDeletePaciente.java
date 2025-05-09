package delete;

import DAO.PacienteDAO;

public class TestDeletePaciente {
    public static void main(String[] args) {
        PacienteDAO pacienteDAO = PacienteDAO.getInstance();
        int idPaciente = 24;

        try {
            pacienteDAO.deleteById(idPaciente);
            System.out.println("Paciente eliminado correctamente.");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}
