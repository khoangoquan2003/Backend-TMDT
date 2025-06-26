package com.example.beprojectweb.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

public class CustomHandshakeHandler extends DefaultHandshakeHandler {

    private final JwtDecoder jwtDecoder;

    public CustomHandshakeHandler(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        if (!(request instanceof ServletServerHttpRequest servletRequest)) {
            return super.determineUser(request, wsHandler, attributes);
        }

        HttpServletRequest httpRequest = servletRequest.getServletRequest();

        // Giả sử token được gửi trong query param `token`
        String token = httpRequest.getParameter("token");
        if (token == null) {
            return super.determineUser(request, wsHandler, attributes);
        }

        try {
            Jwt jwt = jwtDecoder.decode(token);
            String userId = jwt.getSubject(); // hoặc lấy claim userId nếu có
            return () -> userId;
        } catch (Exception e) {
            e.printStackTrace();
            return super.determineUser(request, wsHandler, attributes);
        }
    }
}
