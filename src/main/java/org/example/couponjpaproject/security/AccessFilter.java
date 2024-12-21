package org.example.couponjpaproject.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.couponjpaproject.beans.User;
import org.example.couponjpaproject.login_manager.ClientType;
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
            String token = request.getHeader("Authorization").replace("Bearer ", "");
            //stores current user info
            User user = activeTokens.get(token);
            String role = user.getClientType();
            //current user path
            String requestPath = request.getRequestURI();
            //check user has not timed out
            if (user.getLastRequest().isBefore(Instant.now().minusSeconds(30 * 60))) {
                tokenManager.removeToken(token);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Session Timed out, Please login again.");
                return;
            }

            //perform check to see if user has the required role for the request
            if (requestPath.startsWith("/admin") && !role.equalsIgnoreCase(ClientType.Administrator.name())) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403 Forbidden
                response.getWriter().write("Access Denied: Admins only");
                return;
            }
            if (requestPath.startsWith("/company") && !role.equalsIgnoreCase(ClientType.Company.name())) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403 Forbidden
                response.getWriter().write("Access Denied: Companies only");
                return;
            }
            if (requestPath.startsWith("/customer") && !role.equalsIgnoreCase(ClientType.Customer.name())) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403 Forbidden
                response.getWriter().write("Access Denied: Customers only");
                return;
            }
            user.setLastRequest(Instant.now());
            filterChain.doFilter(request, response);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

    }

}
