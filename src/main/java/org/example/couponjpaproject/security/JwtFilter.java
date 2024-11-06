package org.example.couponjpaproject.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@Order(2)
public class JwtFilter extends OncePerRequestFilter {

    //This is the list of the currently active users on the website, every token has only 30 min of active time
    // and will be removed by a job Thread at its expiration.
    private List<String> activeTokens;

    public JwtFilter(List<String> activeTokens) { //TODO why is this dependency injection?
        this.activeTokens = activeTokens;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException {
        try {
            // at first, we need to check what type of request we recieved
            // we want the authorization header as it contains our token
            // currently still coded in JWT
            String token = request.getHeader("Authorization");
            if (activeTokens.contains(token)) {
                //we want to work with the data in the token so its time to decode it
                //The convention is to start every token with "Bearer " so we need to start by removing it.
                DecodedJWT decodedJWT = JWT.decode(token.replace("Bearer ", ""));
                //We need to check who issued the token to make sure its us, from here:
                //TODO needs to set an issuer here.
                if (decodedJWT.getIssuer().equals("")) {
                    //if it has been issued by our site, move on to next filter
                    filterChain.doFilter(request, response);
                } else {
                    response.setStatus(401);//Unauthorized status
                    response.getWriter().write("Unauthorized, please log in!");
                }
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
        return request.getServletPath().startsWith("*");
    }

    /*
    Summary:
    This filters starts with checking if the current request is authorized to work on our website by
    requiring the token to exist within our active token list. if it in there we will decode and then make sure
    its us who issued it to the user. we can also get more information from within it.
    in this case, we're moving on to the next filter in line.
    additionally, theres an option to decide where on our website we dont require a filter on, this is set by allowing
    certain ends points to go unfiltered.
         */
}
