package com.example.beprojectweb.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    String username;

    @JsonIgnore
    String password;

    String firstName;

    String lastName;


//    @Column(unique = true, nullable = false)
    String email;

    LocalDate dob;

    String phoneNumber;

    boolean enabled = false;
    @Column(name = "avatar_url")
    String avatarUrl;


    @Column(name = "verification_code")
    String verificationCode;
    @Column(name = "verification_expiration")
    LocalDateTime verificationCodeExpiredAt;

    @ElementCollection
    Set<String> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

}

