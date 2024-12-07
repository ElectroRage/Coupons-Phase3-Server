package org.example.couponjpaproject.tokenManager;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import jakarta.annotation.PreDestroy;
import org.example.couponjpaproject.tokenManager.TokenExceptions.InvalidTokenException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class TokenManager {

    private final ConcurrentHashMap<String, String> activeTokens;

    public TokenManager(ConcurrentHashMap<String, String> activeTokens) {
        this.activeTokens = activeTokens;
    }


    @PreDestroy
    public void onDestroy() {
        System.out.println("Token cleanup service shutting down");
    }

    @Scheduled(fixedRate = 10_000)
    public void run() {
        //This can be outside of the loop as this method will initiate itself every 10 seconds
        Date now = new Date(System.currentTimeMillis());
        Iterator<Map.Entry<String, String>> iterator = activeTokens.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            try {
                Date expiresAt = JWT.decode(entry.getValue()).getExpiresAt();
                if (expiresAt.before(now)) {
                    iterator.remove();
                    System.out.println("Removed expired token: " + entry.getValue());
                }
            } catch (JWTDecodeException e) {
                System.out.println(e.getMessage());
                iterator.remove();
                System.out.println("Removed Invalid token: " + entry.getValue());
            }

        }

    }

    public String tokenGenerator(String email) {
        int expTimeMillis = 30 * 60 * 1000;// 30 min
        String token = JWT.create()
                .withIssuer("CouponProject E.O")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + expTimeMillis))
                .withClaim("user", email)
                //TODO: Could be cool to implement an algorithem if i have enough time.
                .sign(Algorithm.none());
        activeTokens.put(email, token);
        return "Bearer " + token;
    }

    public void logout(String token) {
        String activeToken = token.substring(6);
        try {
            activeTokens.remove(activeToken);
        } catch (InvalidTokenException e) {
            System.out.println("Invalid Token: " + e.getMessage());
        }


    }
}
