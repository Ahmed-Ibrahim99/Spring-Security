package com.amaghrabi.security.config;

import com.amaghrabi.security.config.jwt.JwtService;
import com.amaghrabi.security.repository.JwtTokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final JwtTokenRepository jwtTokenRepository;

    public JwtAuthenticationFilter(JwtService jwtService,
                                   UserDetailsService userDetailsService,
                                   JwtTokenRepository jwtTokenRepository) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.jwtTokenRepository = jwtTokenRepository;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        // If the request is to the authentication endpoint, bypass the filter
        String requestPath = request.getServletPath();
        if (requestPath.contains("/api/v1/auth") ||
                requestPath.contains("/paymob-payment/create") ||
                requestPath.contains("/stripe-payment/**")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Retrieve the Authorization header from the request
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String email;

        // If no Authorization header or it doesn't start with "Bearer ", bypass the filter
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract JWT token from the Authorization header
        jwt = authHeader.substring(7);
        // Extract email from the JWT token
        email = jwtService.extractEmailFromToken(jwt);

        // If email is not null and there's no existing authentication in the context
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Load user details using the email
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);
            // Check if the token exists in the repository and is not expired
            boolean isTokenValid = jwtTokenRepository.findByToken(jwt)
                    .map(t -> !t.isExpired())
                    .orElse(false);
            // Validate the token and if it's valid, set the authentication in the security context
            if (jwtService.isTokenValid(jwt, userDetails) && isTokenValid) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        // Continue with the filter chain
        filterChain.doFilter(request, response);
    }
}
