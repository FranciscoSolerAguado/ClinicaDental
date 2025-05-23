package model;

import java.util.Objects;

public class Tratamiento {
    private int idTratamiento;
    private String descripcion;
    private double precio;
    private Dentista dentista;

    public Tratamiento() {

    }

    public Tratamiento (String descripcion, double precio, Dentista dentista) {
        this.descripcion = descripcion;
        this.precio = precio;
        this.dentista = dentista;
    }

    public Tratamiento(int idTratamiento, String descripcion, double precio, Dentista dentista) {
        this.idTratamiento = idTratamiento;
        this.descripcion = descripcion;
        this.precio = precio;
        this.dentista = dentista;
    }

    public int getIdTratamiento() {
        return idTratamiento;
    }

    public void setIdTratamiento(int idTratamiento) {
        this.idTratamiento = idTratamiento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Dentista getDentista() {
        return dentista;
    }

    public void setDentista(Dentista dentista) {
        this.dentista = dentista;
    }



    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Tratamiento that = (Tratamiento) o;
        return idTratamiento == that.idTratamiento;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idTratamiento);
    }

    @Override
    public String toString() {
        return "Tratamiento{" +
                "idTratamiento=" + idTratamiento +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                ", dentista=" + dentista +
                '}';
    }
}
