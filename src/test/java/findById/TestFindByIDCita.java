package findById;

import DAO.CitaDAO;
import model.Cita;

public class TestFindByIDCita {
    public static void main(String[] args) {
        int id = 1;
        Cita cita = CitaDAO.findByIdEager(id);

        if (cita != null) {
            System.out.println("Cita encontrada: " + cita);
        } else {
            System.out.println("Cita no encontrada con ID: " + id);
        }
    }
}
