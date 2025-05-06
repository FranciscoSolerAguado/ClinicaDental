package model;

import java.time.LocalDate;
import java.util.Objects;

public class Cita {
    private int idCita;
    private int idPaciente;
    private int idDentista;
    private Paciente paciente;
    private Dentista dentista;
    private LocalDate fecha;

    public Cita() {
    }

    public Cita(int idCita, int idPaciente, int idDentista, LocalDate fecha) {
        this.idCita = idCita;
        this.idPaciente = idPaciente;
        this.idDentista = idDentista;
        this.fecha = fecha;
    }


    public int getIdCita() {
        return idCita;
    }

    public void setIdCita(int idCita) {
        this.idCita = idCita;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public int getIdDentista() {
        return idDentista;
    }

    public void setIdDentista(int idDentista) {
        this.idDentista = idDentista;
    }

    public Dentista getDentista() {
        return dentista;
    }

    public void setDentista(Dentista dentista) {
        this.dentista = dentista;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Cita cita = (Cita) o;
        return idCita == cita.idCita;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idCita);
    }

    @Override
    public String toString() {
        return "Cita{" +
                "idCita=" + idCita +
                ", idPaciente=" + idPaciente +
                ", paciente=" + paciente +
                ", idDentista=" + idDentista +
                ", dentista=" + dentista +
                ", fecha='" + fecha + '\'' +
                '}';
    }
}
