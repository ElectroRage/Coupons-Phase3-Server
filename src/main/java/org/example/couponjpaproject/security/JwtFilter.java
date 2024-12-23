package org.example.couponjpaproject.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.couponjpaproject.beans.User;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

//@Component
//@Order(2)
public class JwtFilter extends OncePerRequestFilter {

    //This is the list of the currently active users on the website, every token has only 30 min of active time
    // and will be removed by a job Thread at its expiration.
    private final ConcurrentHashMap<String, User> activeTokens;

    public JwtFilter(ConcurrentHashMap<String, User> activeTokens) {
        this.activeTokens = activeTokens;
    }

    //TODO:maybe need to write more checks here.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException {
        try {
            // at first, we need to check what type of request we recieved
            // we want the authorization header as it contains our token
            // currently still coded in JWT
            String token = request.getHeader("Authorization").replace("Bearer ", "");
            if (activeTokens.contains(token)) {
                //we want to work with the data in the token so its time to decode it
                //The convention is to start every token with "Bearer " so we need to start by removing it.
                //We need to check who issued the token to make sure its us, from here:

            }

        } catch (Exception e) {
            // One of the JWT Methods failed and threw an exception which needs to be handled
            response.setStatus(400); // Bad Request
            response.getWriter().write("Bad Request.. Please Check Your Credentials.");

        }
    }

    @Override
    //Added manually, mainly as a reminder that this is an options.
    //Currently set to filter nothing.
    protected boolean shouldNotFilter(HttpServletRequest request) {

        return request.getServletPath().startsWith("/login");
    }


}