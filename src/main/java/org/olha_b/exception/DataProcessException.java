package org.olha_b.exception;

public class DataProcessException extends RuntimeException {
    public DataProcessException(String message) {
        super(message);
    }

    public DataProcessException(String message, Throwable cause) {
        super(message, cause);
    }
}
