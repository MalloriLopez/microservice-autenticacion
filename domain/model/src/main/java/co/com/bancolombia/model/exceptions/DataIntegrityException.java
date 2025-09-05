package co.com.bancolombia.model.exceptions;

public class DataIntegrityException extends RuntimeException {
    public DataIntegrityException(String message) { super(message); }
}
