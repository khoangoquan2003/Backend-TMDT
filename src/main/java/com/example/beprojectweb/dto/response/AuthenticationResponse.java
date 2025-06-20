package com.example.beprojectweb.dto.response;

import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationResponse {
    private boolean authenticated;
    private String token;
    UserResponse userResponse;
}
