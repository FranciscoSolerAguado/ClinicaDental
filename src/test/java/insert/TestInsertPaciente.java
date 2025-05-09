package insert;

import DAO.PacienteDAO;
import model.Paciente;

import java.time.LocalDate;

public class TestInsertPaciente {
    public static void main(String[] args) {
        PacienteDAO pacienteDAO = PacienteDAO.getInstance();

        // Crear un objeto Paciente con datos de prueba
        Paciente paciente = new Paciente();
        paciente.setNombre("María González Ruiz");
        paciente.setDni("45678912B");
        paciente.setTelefono(647892345);
        paciente.setAlergias("Penicilina");
        paciente.setFechaNacimiento(LocalDate.of(1987, 3, 12));
        paciente.setEdad(36);

        // Insertar el paciente en la base de datos
        try {
            pacienteDAO.insert(paciente);
            System.out.println("Paciente insertado correctamente.");
        } catch (RuntimeException e) {
            System.err.println("Error al insertar el paciente: " + e.getMessage());
        }
    }
}
