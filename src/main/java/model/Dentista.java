package model;

import java.util.List;
import java.util.Objects;

public class Dentista extends Persona {
    private int idDentista;
    private String fechaNacimiento;
    private int edad;
    private List<Tratamiento> tratamientosDentista;
    private List<Dentista> dentistas;

    public Dentista(){
        super();

    }

    public Dentista(String nombre, String dni, int telefono, int idDentista, String fechaNacimiento, int edad) {
        super(nombre, dni, telefono);
        this.idDentista = idDentista;
        this.fechaNacimiento = fechaNacimiento;
        this.edad = edad;
    }

    public List<Dentista> getDentistas() {
        return dentistas;
    }

    public void setDentistas(List<Dentista> dentistas) {
        this.dentistas = dentistas;
    }

    public int getIdDentista() {
        return idDentista;
    }

    public void setIdDentista(int idDentista) {
        this.idDentista = idDentista;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public List<Tratamiento> getTratamientosDentista() {
        return tratamientosDentista;
    }

    public void setTratamientosDentista(List<Tratamiento> tratamientosDentista) {
        this.tratamientosDentista = tratamientosDentista;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Dentista dentista = (Dentista) o;
        return idDentista == dentista.idDentista;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idDentista);
    }

    @Override
    public String toString() {
        return "Dentista{" +
                "idDentista=" + idDentista +
                ", fechaNacimiento='" + fechaNacimiento + '\'' +
                ", edad=" + edad +
                ", tratamientosDentista=" + tratamientosDentista +
                '}';
    }
}
