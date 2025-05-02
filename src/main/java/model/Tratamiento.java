package model;

import java.util.List;
import java.util.Objects;

public class Tratamiento {
    private int idTratamiento;
    private TipoTratamiento tipoTratamiento;
    private String nombrePaciente;
    private Dentista dentista;
    private String descripcion;
    private double precio;

    public Tratamiento() {

    }

    public Tratamiento(int idTratamiento, TipoTratamiento tipoTratamiento, String nombrePaciente, Dentista dentista,  String descripcion, double precio) {
        this.idTratamiento = idTratamiento;
        this.tipoTratamiento = tipoTratamiento;
        this.nombrePaciente = nombrePaciente;
        this.dentista = dentista;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public int getIdTratamiento() {
        return idTratamiento;
    }

    public void setIdTratamiento(int idTratamiento) {
        this.idTratamiento = idTratamiento;
    }

    public TipoTratamiento getTipoTratamiento() {
        return tipoTratamiento;
    }

    public void setTipoTratamiento(TipoTratamiento tipoTratamiento) {
        this.tipoTratamiento = tipoTratamiento;
    }

    public Dentista getDentista() {
        return dentista;
    }

    public void setDentista(Dentista dentista) {
        this.dentista = dentista;
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
                ", tipoTratamiento=" + tipoTratamiento +
                ", nombrePaciente='" + nombrePaciente + '\'' +
                ", dentista=" + dentista +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                '}';
    }
}
