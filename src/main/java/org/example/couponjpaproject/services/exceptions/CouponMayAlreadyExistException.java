package org.example.couponjpaproject.services.exceptions;

public class CouponMayAlreadyExistException extends Exception {
    public CouponMayAlreadyExistException(String s) {
        super(s);
    }
}
