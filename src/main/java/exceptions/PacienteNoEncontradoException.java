package exceptions;

public class PacienteNoEncontradoException extends RuntimeException {
    public PacienteNoEncontradoException(String message) {
        super(message);
    }
}
