package org.example.couponjpaproject.controllers;

import org.springframework.web.bind.annotation.RestController;

@RestController
public abstract class ClientController {

    public abstract boolean login(String email, String password);
}
