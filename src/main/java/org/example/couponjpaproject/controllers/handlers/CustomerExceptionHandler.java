package org.example.couponjpaproject.controllers.handlers;


import org.example.couponjpaproject.services.exceptions.CouponIsExpiredException;
import org.example.couponjpaproject.services.exceptions.OwnedCouponException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomerExceptionHandler {

    @ExceptionHandler({OwnedCouponException.class, CouponIsExpiredException.class})
    public ResponseEntity<?> couponExceptionHandler(Exception exception){
        return ResponseEntity.status(403).body(exception.getMessage());
    }
}
