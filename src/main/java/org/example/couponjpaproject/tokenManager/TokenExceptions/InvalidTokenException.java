package org.example.couponjpaproject.tokenManager.TokenExceptions;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException() {
        super("Something went wrong - Invalid Token");
    }
}
