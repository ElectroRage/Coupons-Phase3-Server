package org.example.couponjpaproject.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Order(1)
public class CorsFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Every Address can go to every endpoint on our server.
        response.setHeader("Access-Control-Allow-Origin", "*");
        // "Options is the client asking "Hey what am i allowed to do here, Thanks!".
        // What Methods can be used by the client
        response.setHeader("Access-Control-Allow-Methods", "OPTIONS, GET, DELETE, POST, PUT, HEAD");
        response.setHeader("Access-Control-Allow-Headers",
                "Authorization, Origin, Accept, content-type" +
                        ",Access-Control-Request-Method," +
                        " Access-Control-Request-Headers");
        if (request.getMethod().equals("OPTIONS")) {
            //This is called Pre-Flight in the browser,
            // We can think of this as requesting permission for takeoff.
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
        } else {
            filterChain.doFilter(request, response);// move on to next filter
        }
    }

    /*
    Summary:
    This filter is the first in line in every request our website will receive,
    meaning that at first the request will be granted "permission for take off", when it is sent with "OPTIONS" as
    its method and then it will pass again with its actual method,
    and at that point it will be directed to the next filter in line.
     */
}
