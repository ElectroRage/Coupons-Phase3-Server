package org.example.couponjpaproject.controllers.handlers;

import org.example.couponjpaproject.controllers.CompanyController;
import org.example.couponjpaproject.services.exceptions.CouponIsExpiredException;
import org.example.couponjpaproject.services.exceptions.CouponMayAlreadyExistException;
import org.example.couponjpaproject.services.exceptions.CouponMayNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice(assignableTypes = CompanyController.class)
public class CompanyExceptionHandler {

    @ExceptionHandler(CouponMayAlreadyExistException.class)
    public ResponseEntity<String> handleCouponMayAlreadyExistException(CouponMayAlreadyExistException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(CouponIsExpiredException.class)
    public ResponseEntity<String> handleCouponIsExpiredException(CouponIsExpiredException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(CouponMayNotExistException.class)
    public ResponseEntity<String> handleCouponMayNotExistException(CouponMayNotExistException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception e) {
        System.out.println(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());

    }
}
