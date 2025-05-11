package exceptions;

public class TratamientoPacienteNoEncontrado extends RuntimeException {
    public TratamientoPacienteNoEncontrado(String message) {
        super(message);
    }
}
