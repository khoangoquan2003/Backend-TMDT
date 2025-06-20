package com.example.beprojectweb.dto.request.admin;

import com.example.beprojectweb.enums.Role;

public class CreateStaffRequest {
    private String fullName;
    private String email;
    private String password;
    private Role role;
}
