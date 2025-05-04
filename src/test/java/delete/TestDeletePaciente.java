package delete;

import DAO.PacienteDAO;

public class TestDeletePaciente {
    public static void main(String[] args) {
        int idPaciente = 23;

        try {
            PacienteDAO.deleteById(idPaciente);
            System.out.println("Paciente eliminado correctamente.");
        } catch (RuntimeException e) {
            System.out.println("Error al eliminar el paciente: " + e.getMessage());
        }
    }
}
