package com.example.beprojectweb.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    UUID id;
    String username;
    String firstName;
    String lastName;
    String email;
    String verificationCode;
    String phoneNumber;
    String avatarUrl;
    LocalDateTime verificationCodeExpireAt;
    String dob;
    Set<String> roles;
}
