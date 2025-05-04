package update;

import DAO.CitaDAO;

public class TestUpdateCita {
    public static void main(String[] args) {
        int idCita = 22;
        String nuevaFecha = "2025-04-01";

        try {
            CitaDAO.updateFecha(idCita, nuevaFecha);
            System.out.println("Cita actualizada correctamente.");
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

    }
}
