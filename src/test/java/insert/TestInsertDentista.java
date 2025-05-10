package insert;

import DAO.DentistaDAO;
import model.Dentista;
import utils.Utilidades;

import java.time.LocalDate;

public class TestInsertDentista {
    public static void main(String[] args) {
        DentistaDAO dentistaDAO = DentistaDAO.getInstance();

        String dni = Utilidades.pideDNI("Introduce el DNI del paciente: ");
        int telefono = Utilidades.pideTelefono("Introduce el teléfono del paciente: ");
        String nColegiado = Utilidades.pideNColegiado("Introduce el número de colegiado del dentista: ");

        // Crear un objeto Dentista con datos de prueba
        Dentista dentista = new Dentista();
        dentista.setNombre("Juan Pérez Martínez");
        dentista.setDni(dni);
        dentista.setTelefono(telefono);
        dentista.setnColegiado(nColegiado);
        dentista.setEspecialidad("Implantología");
        dentista.setFechaNacimiento(LocalDate.of(1978, 11, 3));
        dentista.setEdad(45);

        // Insertar el dentista en la base de datos
        try {
            dentistaDAO.insert(dentista);
            System.out.println("Dentista insertado correctamente.");
        } catch (RuntimeException e) {
            System.err.println("Error al insertar el dentista: " + e.getMessage());
        }
    }
}
