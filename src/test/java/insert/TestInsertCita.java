package insert;

import DAO.CitaDAO;
import model.Cita;

public class TestInsertCita {
    public static void main(String[] args) {
        // Crear una nueva cita
        Cita nuevaCita = new Cita();
        nuevaCita.setIdPaciente(23);
        nuevaCita.setIdDentista(15);
        nuevaCita.setNombrePaciente("Jhon Doe");
        nuevaCita.setNombreDentista("Juan Perez Garcia");
        nuevaCita.setFecha("2023-10-01");


        if (nuevaCita != null) {
            CitaDAO.insert(nuevaCita);
            System.out.println("Cita insertada correctamente: " + nuevaCita);
        } else {
            System.out.println("Error al insertar la cita.");
        }
    }
}
