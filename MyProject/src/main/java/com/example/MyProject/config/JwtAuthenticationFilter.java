package com.example.MyProject.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/*
    1. Check if we have a JWT token. (Done in doFilterInternal (if (authHeader == null || !authHeader.startsWith("Bearer"))))
    2. Call UserDetailsService to check if we have the user already in the database. But to do that we need to call JwtService to extract username/email.
 */


//You can use @Service, @Component or @Repository.

@Component //Tell the IoC container that we want this class to be a spring bean
@RequiredArgsConstructor //From lombok: Creates a constructor using any final field we declare
public class JwtAuthenticationFilter extends OncePerRequestFilter {///Everytime a user sends a request we want it to pass this filter

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService; //Interface from Spring Security

    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request, //We can retrieve data from request
        @NonNull HttpServletResponse response, //We can add data to response
        @NonNull FilterChain filterChain //filterChain contains the list of the other filters we need to execute. FilterChain.doFiler() executes the next filter in the chain.
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization"); //Pass the header name in the method. Authorization conatins the JWT token. A JWT token is a bearer token.
        final String jwtToken; //Used to check JWT token
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer")){ //Validation: Bearer token should always start with "Bearer"
            filterChain.doFilter(request, response);
            return;
        }

        jwtToken = authHeader.substring(7); //JWT in header starts after "Bearer" so we start at 7
        userEmail = jwtService.extractUsername(jwtToken);

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) { //1. Check if user is already authenticated. If so, no need to authenticate again.
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail); //2. Get user from database by username or email.

            if (jwtService.isTokenValid(jwtToken, userDetails)) { //3. Check if the user token is valid or not.
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()); //4. If the token is valid, create a UsernamePasswordAuthenticationToken object. This object is needed by Spring and SecurityContextHolder in order to update our SecurityContext.
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); //5. Set details.

                SecurityContextHolder.getContext().setAuthentication(authToken);//6. Update the authentication token with the security context holder.
            }
        }
        filterChain.doFilter(request, response); //Always need to pass on to the next filters that will be executed.

    }

}