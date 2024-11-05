package org.example.couponjpaproject.services.exceptions;

public class CustomerMayNotExistException extends Exception {
    public CustomerMayNotExistException(String s) {
        super(s);
    }
}
