package model;

import java.util.List;
import java.util.Objects;

public class Tratamiento {
    private int idTratamiento;
    private String descripcion;
    private double precio;
    private int idDentista;

    public Tratamiento() {

    }

    public Tratamiento(int idTratamiento, String descripcion, double precio, int idDentista) {
        this.idTratamiento = idTratamiento;
        this.descripcion = descripcion;
        this.precio = precio;
        this.idDentista = idDentista;
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
                ", idDentista=" + idDentista +
                '}';
    }
}
