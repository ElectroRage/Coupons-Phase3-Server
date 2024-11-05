package org.example.couponjpaproject.controllers;

import org.example.couponjpaproject.services.CompanyServices;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class CompanyController extends ClientController{

    CompanyServices service;

    public CompanyController(CompanyServices service) {
        this.service = service;
    }


    @Override
    public boolean login(String email, String password) {
        return false;
    }




}
