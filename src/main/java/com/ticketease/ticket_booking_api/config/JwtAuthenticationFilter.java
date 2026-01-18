package com.ticketease.ticket_booking_api.config;

import com.ticketease.ticket_booking_api.service.CustomUserDetailsService;
import com.ticketease.ticket_booking_api.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, CustomUserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // 1. Get the Authorization Header from the request
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // 2. Check: Does it exist? Does it start with "Bearer "?
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // If not, pass the request along to the next filter (Spring Security will reject it later)
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Extract the Token
        // "Bearer " is 7 characters long. We cut them off to get just the token string.
        jwt = authHeader.substring(7);

        // 4. Extract the Email using our service
        userEmail = jwtService.extractUsername(jwt);

        // 5. Authentication Logic
        // IF we found an email AND the user is not already authenticated in this context...
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Load the user details from the database
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            // Check if the token is valid
            if (jwtService.isTokenValid(jwt, userDetails)) {

                // --- THE KEY MOMENT ---
                // We create an "Authentication Token" (not the JWT, a Spring internal object)
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null, // No credentials needed (we already verified the JWT)
                        userDetails.getAuthorities()
                );

                // Add details (like IP address) to the token
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // Update the SecurityContextHolder
                // This is us telling Spring: "This user is valid. Let them in."
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // 6. Continue the filter chain
        filterChain.doFilter(request, response);
    }
}
