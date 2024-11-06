package org.example.couponjpaproject.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.example.couponjpaproject.login_manager.ClientType;
import org.example.couponjpaproject.login_manager.LoginManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class AuthController {

    LoginManager loginManager;
    private List<String> activeTokens;

    public AuthController(LoginManager loginManager, List<String> activeTokens) {
        this.loginManager = loginManager;
        this.activeTokens = activeTokens;
    }

    @PostMapping("/login")
    public String login(@RequestBody String email, String password, String clientType) {
        loginManager.login(email, password, ClientType.valueOf(clientType));
        String token = tokenGenerator(email);
        activeTokens.add(token);
        return token;
    }

    //TODO: it probably needs to be more complicated than this.
    public void logOut(String token) {
        activeTokens.remove(token);
    }

    private String tokenGenerator(String email) {
        int expTimeMillis = 30 * 60 * 1000;// 30 min
        String token = JWT.create()
                .withIssuer("CouponProject E.O")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + expTimeMillis))
                .withClaim("user",email)
                .sign(Algorithm.none());
        return "Bearer " + token;
    }
}
