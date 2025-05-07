package model;

import java.time.LocalDate;

public class TratamientoPaciente {
    private int idPaciente;
    private int idTratamiento;
    private LocalDate fechaTratamiento;
    private String detalles;

    public TratamientoPaciente() {
    }

    public TratamientoPaciente(int idPaciente, int idTratamiento, LocalDate fechaTratamiento, String detalles) {
        this.idPaciente = idPaciente;
        this.idTratamiento = idTratamiento;
        this.fechaTratamiento = fechaTratamiento;
        this.detalles = detalles;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public int getIdTratamiento() {
        return idTratamiento;
    }

    public void setIdTratamiento(int idTratamiento) {
        this.idTratamiento = idTratamiento;
    }

    public LocalDate getFechaTratamiento() {
        return fechaTratamiento;
    }

    public void setFechaTratamiento(LocalDate fechaTratamiento) {
        this.fechaTratamiento = fechaTratamiento;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    @Override
    public String toString() {
        return "TratamientoPaciente{" +
                "idPaciente=" + idPaciente +
                ", idTratamiento=" + idTratamiento +
                ", fechaTratamiento=" + fechaTratamiento +
                ", detalles='" + detalles + '\'' +
                '}';
    }
}
