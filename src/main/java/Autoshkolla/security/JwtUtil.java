package Autoshkolla.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    private static final String SECRET = "autoshkolla-secret-key-must-be-very-long-and-secure-32-bytes!!";
    private static final long ACCESS_EXPIRY  = 15 * 60 * 1000;        // 15 minuta
    private static final long REFRESH_EXPIRY = 7 * 24 * 60 * 60 * 1000; // 7 ditë

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public String generateAccessToken(String email, String role) {
        return Jwts.builder()
                .subject(email)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + ACCESS_EXPIRY))
                .signWith(getKey())
                .compact();
    }

    public String generateRefreshToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRY))
                .signWith(getKey())
                .compact();
    }

    public Claims validateToken(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isValid(String token) {
        try { 
            validateToken(token); 
            return true; 
        } catch (Exception e) { 
            return false; 
        }
    }

    public String getEmail(String token) {
        return validateToken(token).getSubject();
    }

    public String getRole(String token) {
        return validateToken(token).get("role", String.class);
    }
}