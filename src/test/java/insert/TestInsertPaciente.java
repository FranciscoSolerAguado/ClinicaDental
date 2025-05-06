package insert;

import DAO.PacienteDAO;
import model.Paciente;

import java.time.LocalDate;

public class TestInsertPaciente {
    public static void main(String[] args) {
        // Crear un objeto Paciente con datos de prueba
        Paciente paciente = new Paciente();
        paciente.setNombre("Juan Vaquero Perez");
        paciente.setDni("23957923A");
        paciente.setTelefono(638088251);
        paciente.setFechaNacimiento(LocalDate.of(1990, 1, 1));
        paciente.setEdad(35);

        // Insertar el paciente en la base de datos
        try {
            PacienteDAO.insert(paciente);
            System.out.println("Paciente insertado correctamente.");
        } catch (RuntimeException e) {
            System.err.println("Error al insertar el paciente: " + e.getMessage());
        }
    }
}
