package org.example.couponjpaproject.tokenManager;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import jakarta.annotation.PreDestroy;
import org.example.couponjpaproject.beans.User;
import org.example.couponjpaproject.tokenManager.TokenExceptions.InvalidTokenException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class TokenManager {

    private final ConcurrentHashMap<String, User> activeTokens;

    public TokenManager(ConcurrentHashMap<String, User> activeTokens) {
        this.activeTokens = activeTokens;
    }


    @PreDestroy
    //When Service Shuts-down
    public void onDestroy() {
        System.out.println("Token cleanup service shutting down");
    }

    @Scheduled(fixedRate = 10_000)
    public void run() {
        //This can be outside of the loop as this method will initiate itself every 10 seconds
        Instant now = Instant.now();
        //Shadow Copy of hashmap to run over
        Iterator<Map.Entry<String, User>> iterator = activeTokens.entrySet().iterator();
        //while there's another entry
        while (iterator.hasNext()) {
            //load it
            Map.Entry<String, User> entry = iterator.next();
            try {
                Instant lastRequest = entry.getValue().getLastRequest();
                // if the last request occurred before this instant minus 30minutes
                if (lastRequest.isBefore(now.minusSeconds(30 * 60))) {
                    // remove the object from the iterator and at the same time from the hashmap.
                    iterator.remove();
                    System.out.println("Removed expired token: " + entry.getKey().replace("Bearer ", ""));
                }
            } catch (RuntimeException e) {
                // If There's an error with the information recieved, throw an exception and remove the user.
                iterator.remove();
                System.out.println("An unknown Error has occurred");
            }

        }
    }

    public String tokenGenerator(String email, String clientType) {
        Instant now = Instant.now();
        String token = JWT.create()
                .withIssuer("CouponProject E.O")
                .withClaim("user", email)
                .withIssuedAt(now)
                //TODO: Could be cool to implement an algorithem if i have enough time.
                .sign(Algorithm.none());
        User userData = new User(clientType, now);
        activeTokens.put(token, userData);
        return "Bearer " + token;
    }

    public void logout(String token) throws InvalidTokenException {
        String activeToken = token.replace("Bearer ", "");
        if (!activeTokens.containsKey(activeToken)) {
            throw new InvalidTokenException();
        } else
            removeToken(activeToken);

    }


    //we'll use this method for token removal in other classes
    public void removeToken(String token) {
        activeTokens.remove(token);

    }


}
