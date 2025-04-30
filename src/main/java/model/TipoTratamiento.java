package model;

public enum TipoTratamiento {
    //LOS TIPOS DE TRATAMIENTO QUE SE PUEDEN REALIZAR EN LA CLINICA DENTAL
    EXTRACCION_DE_MUELA("Extraccion de muela"),
    LIMPIEZA_DENTAL("Limpieza dental"),
    ENDODONCIA("Endodoncia"),
    ORTODONCIA("Ortodoncia"),
    IMPLANTE_DENTAL("Implante dental"),
    BLANQUEAMIENTO_DENTAL("Blanqueamiento dental"),
    RECONSTRUCCION_DENTAL("Reconstrucción dental"),
    CORONA_DENTAL("Corona dental"),
    PUENTE_DENTAL("Puente dental"),
    RELLENO_DENTAL("Relleno dental"),
    CIRUGIA_ORAL("Cirugía oral"),
    TRATAMIENTO_DE_ENFERMEDAD_PERIODONTAL("Tratamiento de enfermedad periodontal");

    private final String tratamiento;


    TipoTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public String getTratamiento() {
        return tratamiento;
    }
}
