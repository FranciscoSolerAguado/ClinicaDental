package model;

import java.time.LocalDate;

public class TratamientoPaciente {
    private Paciente paciente;
    private Tratamiento tratamiento;
    private LocalDate fechaTratamiento;
    private String detalles;
    private String pacienteNombre;

    public TratamientoPaciente() {
    }

    public TratamientoPaciente(Paciente paciente, Tratamiento tratamiento, LocalDate fechaTratamiento, String detalles) {
        this.paciente = paciente;
        this.tratamiento = tratamiento;
        this.fechaTratamiento = fechaTratamiento;
        this.detalles = detalles;
    }

    public String getPacienteNombre() {
        return pacienteNombre;
    }

    public void setPacienteNombre(String pacienteNombre) {
        this.pacienteNombre = pacienteNombre;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Tratamiento getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(Tratamiento tratamiento) {
        this.tratamiento = tratamiento;
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
                "paciente=" + paciente +
                ", tratamiento=" + tratamiento +
                ", fechaTratamiento=" + fechaTratamiento +
                ", detalles='" + detalles + '\'' +
                ", pacienteNombre='" + pacienteNombre + '\'' +
                '}';
    }
}
