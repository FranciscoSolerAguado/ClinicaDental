package model;

public class TratamientoDentista {
    private int idDentista;
    private int idTratamiento;

    public TratamientoDentista() {
    }

    public TratamientoDentista(int idDentista, int idTratamiento) {
        this.idDentista = idDentista;
        this.idTratamiento = idTratamiento;
    }

    public int getIdDentista() {
        return idDentista;
    }

    public void setIdDentista(int idDentista) {
        this.idDentista = idDentista;
    }

    public int getIdTratamiento() {
        return idTratamiento;
    }

    public void setIdTratamiento(int idTratamiento) {
        this.idTratamiento = idTratamiento;
    }

    @Override
    public String toString() {
        return "TratamientoDentista{" +
                "idDentista=" + idDentista +
                ", idTratamiento=" + idTratamiento +
                '}';
    }
}
