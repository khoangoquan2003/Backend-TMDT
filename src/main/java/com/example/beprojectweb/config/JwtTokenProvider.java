package com.example.beprojectweb.config;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    private final JwtDecoder jwtDecoder;

    public JwtTokenProvider(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    public String getUsername(String token) {
        try {
            Jwt jwt = jwtDecoder.decode(token);
            return jwt.getSubject();  // Lấy subject từ token
        } catch (JwtException e) {
            return null;
        }
    }

    public boolean validateToken(String token) {
        try {
            Jwt jwt = jwtDecoder.decode(token);
            // Ở đây bạn có thể check thêm ngày hết hạn, scope...
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}
