package insert;

import DAO.PacienteDAO;
import model.Paciente;

public class TestInsertPaciente {
    public static void main(String[] args) {
        // Crear un objeto Paciente con datos de prueba
        Paciente paciente = new Paciente();
        paciente.setNombre("Jhon Doe");
        paciente.setDni("12345678A");
        paciente.setTelefono(5551234);
        paciente.setFechaNacimiento("1990-01-01");
        paciente.setEdad(33);

        // Insertar el paciente en la base de datos
        try {
            PacienteDAO.insert(paciente);
            System.out.println("Paciente insertado correctamente.");
        } catch (RuntimeException e) {
            System.err.println("Error al insertar el paciente: " + e.getMessage());
        }
    }
}
