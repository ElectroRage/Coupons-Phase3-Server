package org.example.couponjpaproject.controllers;

import org.example.couponjpaproject.beans.Coupon;
import org.example.couponjpaproject.services.CustomerServices;
import org.example.couponjpaproject.services.WebServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("general")
public class GeneralController {

    WebServices webServices;

    public GeneralController(WebServices webServices) {
        this.webServices = webServices;
    }

    @RequestMapping("allcoupons")
    public List<Coupon> allCoupons() {
        return webServices.getAllCoupons();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }



}