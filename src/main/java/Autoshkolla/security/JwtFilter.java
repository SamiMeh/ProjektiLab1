package Autoshkolla.security;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String path = request.getRequestURI();

        // Logging to help debug (remove in production)
        System.out.println("Request: " + path);
        System.out.println("Authorization header: " + authHeader);

        // If no token, just continue – SecurityConfig will handle public vs protected paths
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        if (jwtUtil.isValid(token)) {
            String email = jwtUtil.getEmail(token);
            String role = jwtUtil.getRole(token);
            System.out.println("Token valid for: " + email + ", role: " + role);

            // Ensure role has ROLE_ prefix
            if (role != null && !role.startsWith("ROLE_")) {
                role = "ROLE_" + role;
            }

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    email, null, List.of(new SimpleGrantedAuthority(role))
            );
            SecurityContextHolder.getContext().setAuthentication(authToken);
        } else {
            System.out.println("Invalid token");
        }

        filterChain.doFilter(request, response);
    }
}