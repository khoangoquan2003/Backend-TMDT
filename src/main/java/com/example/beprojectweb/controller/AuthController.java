package com.example.beprojectweb.controller;

import com.example.beprojectweb.dto.request.auth.AuthenticationRequest;
import com.example.beprojectweb.dto.request.auth.ForgotPasswordRequest;
import com.example.beprojectweb.dto.request.auth.IntrospectRequest;
import com.example.beprojectweb.dto.request.UserCreationRequest;
import com.example.beprojectweb.dto.request.VerifyUser;
import com.example.beprojectweb.dto.request.auth.ResetPasswordRequest;
import com.example.beprojectweb.dto.response.APIResponse;
import com.example.beprojectweb.dto.response.AuthenticationResponse;
import com.example.beprojectweb.dto.response.IntrospectResponse;
import com.example.beprojectweb.dto.response.UserResponse;
import com.example.beprojectweb.entity.User;
import com.example.beprojectweb.service.AuthenticationService;
import com.example.beprojectweb.service.UserService;
import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.ParseException;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {
    AuthenticationService authenticationService;
    UserService userService;

    @PostMapping("/signup")
    APIResponse<User> createUser(@RequestBody @Valid UserCreationRequest request) {
        APIResponse apiResponse = new APIResponse();
        apiResponse.setResult(authenticationService.createUser(request));
        return apiResponse;
    }

    //Tạo endpoint login
    @PostMapping("/login")
    APIResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        var result = authenticationService.authenticate(request);
        return APIResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/introspect")
    APIResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        var result = authenticationService.introspectResponse(request);
        return APIResponse.<IntrospectResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestBody VerifyUser request) {
        try {
            authenticationService.verifyUser(request);
            return ResponseEntity.ok("Account verified successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/resend")
    public ResponseEntity<?> resendVerificationCode(@RequestParam String email) {
        try {
            authenticationService.resendVerificationCode(email);
            return ResponseEntity.ok("Verification code sent");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getUserProfile(Principal principal) {
        User user = (User) authenticationService.loadUserByUsername(principal.getName());

        if (null == user) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        UserResponse userDetailsDto = UserResponse.builder()
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .id(user.getId())
                .build();

        return new ResponseEntity<>(userDetailsDto, HttpStatus.OK);

    }

    @PostMapping("/forgot-password")
    public ResponseEntity<APIResponse> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        authenticationService.forgotPassword(request);
        return ResponseEntity.ok(APIResponse.builder()
                .result("Verification code sent to your email.")
                .build());
    }

    @PostMapping("/verify-code-reset")
    public ResponseEntity<APIResponse> verifyCode(@RequestBody VerifyUser request) {
        authenticationService.verifyForgotPasswordCode(request);
        return ResponseEntity.ok(APIResponse.builder().result("Code verified. You can now reset your password.").build());
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) {
        authenticationService.resetPassword(request);
        return ResponseEntity.ok("Password updated successfully.");
    }
}
