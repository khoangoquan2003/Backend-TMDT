package com.example.beprojectweb.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;

import java.security.Principal;
import java.util.Map;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final JwtDecoder jwtDecoder;

    // Inject JwtDecoder từ bean trong SecurityConfig
    public WebSocketConfig(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic", "/queue");  // Broker cho topic và queue user riêng
        config.setApplicationDestinationPrefixes("/app");
        config.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setHandshakeHandler(new CustomHandshakeHandler(jwtDecoder))
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    private static class CustomHandshakeHandler extends DefaultHandshakeHandler {

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

            // Lấy token từ query param, ví dụ ws://localhost:8080/ws?token=xxx
            String token = httpRequest.getParameter("token");
            if (token == null) {
                return super.determineUser(request, wsHandler, attributes);
            }

            try {
                Jwt jwt = jwtDecoder.decode(token);
                String userId = jwt.getSubject();  // Hoặc lấy claim phù hợp nếu bạn lưu userId ở claim khác
                return () -> userId;
            } catch (Exception e) {
                e.printStackTrace();
                return super.determineUser(request, wsHandler, attributes);
            }
        }
    }
}
