package com.example.warbox.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

public class JwtUtil {

    private static final String SECRET = "your-very-long-secret-key-here-must-be-at-least-32-characters-long";
    private static final SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());
    private static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000; // 24 hours in milliseconds

    public static String generateToken(UUID userId, String email, String handle) {
        return Jwts.builder()
                .subject(userId.toString())     // Main subject is user ID
                .claim("email", email)          // Add email as custom claim
                .claim("handle", handle)        // Add handle as custom claim
                .issuedAt(new Date())           // When token was created
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // When it expires
                .signWith(key)                  // Sign with our secret key
                .compact();                     // Convert to string
    }

    public static UUID getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return UUID.fromString(claims.getSubject());
    }

    public static boolean isTokenValid(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false; // Token is invalid or expired
        }
    }
}