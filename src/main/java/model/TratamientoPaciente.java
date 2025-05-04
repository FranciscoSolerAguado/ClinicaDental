package model;

public class TratamientoPaciente {
    private int idPaciente;
    private int idTratamiento;

    public TratamientoPaciente() {
    }

    public TratamientoPaciente(int idPaciente, int idTratamiento) {
        this.idPaciente = idPaciente;
        this.idTratamiento = idTratamiento;
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

    @Override
    public String toString() {
        return "TratamientoPaciente{" +
                "idPaciente=" + idPaciente +
                ", idTratamiento=" + idTratamiento +
                '}';
    }
}
