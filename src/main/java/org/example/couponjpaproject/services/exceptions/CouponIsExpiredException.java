package org.example.couponjpaproject.services.exceptions;

public class CouponIsExpiredException extends Exception {
    public CouponIsExpiredException(String message) {
        super(message);
    }
}
