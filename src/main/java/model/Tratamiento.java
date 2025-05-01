package model;

import java.util.List;
import java.util.Objects;

public class Tratamiento {
    private int idTratamiento;
    private TipoTratamiento tipoTratamiento;
    private String nombrePaciente;
    private List<Dentista> dentistas;
    private Paciente paciente;
    private String descripcion;
    private double precio;

    public Tratamiento() {

    }

    public Tratamiento(int idTratamiento, TipoTratamiento tipoTratamiento, String nombrePaciente, List<Dentista> dentistas, Paciente paciente, String descripcion, double precio) {
        this.idTratamiento = idTratamiento;
        this.tipoTratamiento = tipoTratamiento;
        this.nombrePaciente = nombrePaciente;
        this.dentistas = dentistas;
        this.paciente = paciente;
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

    public List<Dentista> getDentistas() {
        return dentistas;
    }

    public void setDentistas(List<Dentista> dentistas) {
        this.dentistas = dentistas;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
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
                ", dentistas=" + dentistas +
                ", paciente=" + paciente +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                '}';
    }
}
