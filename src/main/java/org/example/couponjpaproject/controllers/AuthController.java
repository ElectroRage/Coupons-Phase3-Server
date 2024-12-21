package org.example.couponjpaproject.controllers;


import org.example.couponjpaproject.beans.User;
import org.example.couponjpaproject.login_manager.ClientType;
import org.example.couponjpaproject.login_manager.LoginManager;
import org.example.couponjpaproject.services.ClientServices;
import org.example.couponjpaproject.tokenManager.TokenManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuthController {

    LoginManager loginManager;
    private final TokenManager tokenManager;

    public AuthController(LoginManager loginManager, TokenManager tokenManager) {
        this.loginManager = loginManager;
        this.tokenManager = tokenManager;
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        loginManager.login(user.getEmail(), user.getPassword(), ClientType.valueOf(user.getClientType()));
        return tokenManager.tokenGenerator(user.getEmail(),user.getClientType());
    }

    @PostMapping("/logout")
    public void logOut(@RequestBody String token) {
        tokenManager.logout(token);
    }


}
