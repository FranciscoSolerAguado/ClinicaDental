package model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Dentista extends Persona {
    private int idDentista;
    private String nColegiado;
    private String especialidad;
    private LocalDate fechaNacimiento;
    private int edad;
    private List<Tratamiento> tratamientosDentista;

    public Dentista(){
        super();

    }

    public Dentista(String nombre, String dni, int telefono, int idDentista, LocalDate fechaNacimiento, int edad) {
        super(nombre, dni, telefono);
        this.idDentista = idDentista;
        this.fechaNacimiento = fechaNacimiento;
        this.edad = edad;
    }


    public int getIdDentista() {
        return idDentista;
    }

    public int setIdDentista(int idDentista) {
        this.idDentista = idDentista;
        return idDentista;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
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

    public List<Tratamiento> getTratamientosDentista() {
        return tratamientosDentista;
    }

    public void setTratamientosDentista(List<Tratamiento> tratamientosDentista) {
        this.tratamientosDentista = tratamientosDentista;
    }

    public String getnColegiado() {
        return nColegiado;
    }

    public void setnColegiado(String nColegiado) {
        this.nColegiado = nColegiado;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
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
                ", nColegiado='" + nColegiado + '\'' +
                ", especialidad='" + especialidad + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", edad=" + edad +
                ", tratamientosDentista=" + tratamientosDentista +
                '}';
    }
}
