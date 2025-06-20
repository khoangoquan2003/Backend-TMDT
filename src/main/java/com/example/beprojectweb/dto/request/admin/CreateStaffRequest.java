package com.example.beprojectweb.dto.request.admin;

import com.example.beprojectweb.enums.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateStaffRequest {
    String username;
    String email;
    String password;
    Set<String> roles;
}
