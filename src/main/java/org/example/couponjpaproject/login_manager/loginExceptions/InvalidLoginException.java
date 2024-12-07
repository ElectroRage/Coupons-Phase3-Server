package org.example.couponjpaproject.login_manager.loginExceptions;

public class InvalidLoginException extends RuntimeException {


    public InvalidLoginException(String message) {
        super(message);
    }
}
