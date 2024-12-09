package org.example.couponjpaproject.controllers.handlers;


import org.example.couponjpaproject.controllers.CompanyController;
import org.example.couponjpaproject.services.exceptions.CouponIsExpiredException;
import org.example.couponjpaproject.services.exceptions.OwnedCouponException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = CompanyController.class)
public class CustomerExceptionHandler {

    @ExceptionHandler(OwnedCouponException.class)
    public ResponseEntity<String> handleOwnedCouponException(OwnedCouponException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }


    @ExceptionHandler(CouponIsExpiredException.class)
    public ResponseEntity<String> handleCouponIsExpiredException(CouponIsExpiredException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }
}
