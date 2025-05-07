package update;

import exceptions.CitaNoEncontradaException;

public class TestUpdateCita {
    public static void main(String[] args) {
        int idCita = 22;
        String nuevaFecha = "2025-04-01";

        try {
            CitaDAO.updateFecha(idCita, nuevaFecha);
            System.out.println("Cita actualizada correctamente.");
        } catch (CitaNoEncontradaException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }
}
