package org.example.couponjpaproject.controllers.handlers;

import org.example.couponjpaproject.services.exceptions.CompanyAlreadyExistsException;
import org.example.couponjpaproject.services.exceptions.CompanyMayNotExistException;
import org.example.couponjpaproject.services.exceptions.CustomerAlreadyExistsException;
import org.example.couponjpaproject.services.exceptions.CustomerMayNotExistException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class AdminExceptionHandler {

    @ExceptionHandler({CompanyAlreadyExistsException.class, CustomerAlreadyExistsException.class})
    public ResponseEntity<?> handleAlreadyExistsException(Exception exception){
        //forbidden
        return ResponseEntity.status(403).body(exception.getMessage());
    }

    @ExceptionHandler({CompanyMayNotExistException.class, CustomerMayNotExistException.class})
    public ResponseEntity<?> handleMayNotExistsException(Exception exception){
        //Not found
        return ResponseEntity.status(404).body(exception.getMessage());
    }


}
