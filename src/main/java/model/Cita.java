package model;

import java.util.Objects;

public class Cita {
    private int idCita;
    private int idPaciente;
    private int idDentista;
    private String nombrePaciente;
    private String nombreDentista;
    private Paciente paciente;
    private Dentista dentista;
    private String fecha;

    public Cita() {
    }

    public Cita(int idCita, int idPaciente, int idDentista, String nombrePaciente, String nombreDentista, String fecha) {
        this.idCita = idCita;
        this.idPaciente = idPaciente;
        this.idDentista = idDentista;
        this.nombrePaciente = nombrePaciente;
        this.nombreDentista = nombreDentista;
        this.fecha = fecha;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public String getNombreDentista() {
        return nombreDentista;
    }

    public void setNombreDentista(String nombreDentista) {
        this.nombreDentista = nombreDentista;
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
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
                ", idDentista=" + idDentista +
                ", nombrePaciente='" + nombrePaciente + '\'' +
                ", nombreDentista='" + nombreDentista + '\'' +
                ", paciente=" + paciente +
                ", dentista=" + dentista +
                ", fecha='" + fecha + '\'' +
                '}';
    }
}
