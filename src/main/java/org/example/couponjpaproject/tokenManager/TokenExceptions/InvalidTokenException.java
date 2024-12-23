package org.example.couponjpaproject.tokenManager.TokenExceptions;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String message) {
        super(message);
    }
}
