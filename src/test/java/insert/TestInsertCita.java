package insert;

import java.time.LocalDate;

public class TestInsertCita {
    public static void main(String[] args) {
        // Crear una nueva cita
        Cita nuevaCita = new Cita();
        nuevaCita.setIdPaciente(21);
        nuevaCita.setIdDentista(15);
        nuevaCita.setFecha(LocalDate.of(2023, 10, 1));


        if (nuevaCita != null) {
            CitaDAO.insert(nuevaCita);
            System.out.println("Cita insertada correctamente: " + nuevaCita);
        } else {
            System.out.println("Error al insertar la cita.");
        }
    }
}
