package org.example.couponjpaproject.services.exceptions;

public class CouponMayNotExistException extends Exception {
    public CouponMayNotExistException(String s) {
        super(s);
    }
}
