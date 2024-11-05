package org.example.couponjpaproject.services.exceptions;

public class CustomerAlreadyExistsException extends Exception {
    public CustomerAlreadyExistsException(String thisCustomersEmailAlreadyExist) {
        super(thisCustomersEmailAlreadyExist);
    }
}
