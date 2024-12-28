package org.example.couponjpaproject.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.couponjpaproject.beans.User;
import org.example.couponjpaproject.tokenManager.TokenExceptions.InvalidTokenException;
import org.example.couponjpaproject.tokenManager.TokenManager;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Order(2)
public class AccessFilter extends OncePerRequestFilter {

    private final ConcurrentHashMap<String, User> activeTokens;
    private final TokenManager tokenManager;

    public AccessFilter(ConcurrentHashMap<String, User> activeTokens, TokenManager tokenManager) {
        this.activeTokens = activeTokens;
        this.tokenManager = tokenManager;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {

            String token = request.getHeader("Authorization").substring(7);
            if (activeTokens.containsKey(token)) {

                //stores current user info
                DecodedJWT decodedJWT = JWT.decode(token);
                User user = activeTokens.get(token);
                String role = user.getClientType();

                //current user path
                String requestPath = request.getRequestURI();
                if (!decodedJWT.getIssuer().equals("CouponProject E.O")) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().write("Unauthorized token");
                    return;
                }

                //check logout request
                // might contain invalid tokens or empty ones.

                if (requestPath.startsWith("/logout/")) {
                    try {
                        String pathToken = requestPath.substring(17);
                        if (!activeTokens.containsKey(pathToken)) {
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            response.getWriter().write("Invalid Token, login and try again");
                        }
                    } catch (Exception e) {
                        response.getWriter().write(e.getMessage());
                    }
                }

                //perform check to see if user has the required role for the request
                if (requestPath.startsWith("/admin/") && !role.equals("Administrator")) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().write("Access Denied: Admins only");
                    return;
                }
                if (requestPath.startsWith("/company/") && !role.equals("Company")) {

                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().write("Access Denied: Companies only");
                    return;
                }
                if (requestPath.startsWith("/customer/") && !role.equals("Customer")) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().write("Access Denied: Customers only");
                    return;

                }

                //update users last request and allow access
                user.setLastRequest(Instant.now());
                filterChain.doFilter(request, response);

            } else {
                //TODO: Make sure the front end re-routes to login page upon this error
                response.setStatus(401);//Unauthorized status
                response.getWriter().write("Unauthorized Access: please login again");
            }

        } catch (Exception e) {
            // One of the JWT Methods failed and threw an exception which needs to be handled
            response.setStatus(400); // Bad Request
            response.getWriter().write("Bad Request.. Please Check Your Credentials.");

        }
    }

//    @Override
//    protected boolean shouldNotFilter(HttpServletRequest request) {
//        return request.getServletPath().startsWith("/login");
//    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getServletPath().startsWith("/");
    }



}
