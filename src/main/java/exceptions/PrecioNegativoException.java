package exceptions;

public class PrecioNegativoException extends RuntimeException {
    public PrecioNegativoException(String message) {
        super(message);
    }
}
