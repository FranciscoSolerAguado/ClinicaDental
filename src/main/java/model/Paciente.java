package model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Paciente extends Persona{
    private int idPaciente;
    private String alergias;
    private LocalDate fechaNacimiento;
    private int edad;
    private List<Tratamiento> tratamientosPaciente;

    public Paciente() {

    }

    public Paciente(String nombre, String dni, int telefono, int idPaciente, String alergias, LocalDate fechaNacimiento, int edad, List<Tratamiento> tratamientosPaciente) {
        super(nombre, dni, telefono);
        this.idPaciente = idPaciente;
        this.alergias = alergias;
        this.fechaNacimiento = fechaNacimiento;
        this.edad = edad;
        this.tratamientosPaciente = tratamientosPaciente;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getAlergias() {
        return alergias;
    }

    public void setAlergias(String alergias) {
        this.alergias = alergias;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public List<Tratamiento> getTratamientosPaciente() {
        return tratamientosPaciente;
    }

    public void setTratamientosPaciente(List<Tratamiento> tratamientosPaciente) {
        this.tratamientosPaciente = tratamientosPaciente;
    }



    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Paciente paciente = (Paciente) o;
        return idPaciente == paciente.idPaciente;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idPaciente);
    }

    @Override
    public String toString() {
        return "Paciente{" +
                "idPaciente=" + idPaciente +
                ", alergias='" + alergias + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", edad=" + edad +
                ", tratamientosPaciente=" + tratamientosPaciente +
                '}';
    }
}
