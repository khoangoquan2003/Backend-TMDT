package com.example.beprojectweb.controller;

import com.example.beprojectweb.dto.request.UserUpdateRequest;
import com.example.beprojectweb.dto.request.admin.CreateStaffRequest;
import com.example.beprojectweb.dto.request.auth.ResetPasswordRequest;
import com.example.beprojectweb.dto.response.APIResponse;
import com.example.beprojectweb.dto.response.UserResponse;
import com.example.beprojectweb.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {
    UserService userService;

//    @PostMapping
//    APIResponse<User> createUser(@RequestBody @Valid UserCreationRequest request) {
//        APIResponse apiResponse = new APIResponse();
//        apiResponse.setResult(userService.createUser(request));
//        return apiResponse;
//    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    APIResponse<List<UserResponse>> getUsers() {
        return APIResponse.<List<UserResponse>>builder()
                .result(userService.getUsers())
                .build();
    }

    @GetMapping("/{userId}")
    APIResponse<UserResponse> getUserById(@PathVariable UUID userId){
        return APIResponse.<UserResponse>builder()
                .result(userService.getUserById(userId))
                .build();

    }

    @GetMapping("/myInfo")
    APIResponse<UserResponse> getMyInfo(){
        return APIResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }

    @PutMapping("/me/update")
    APIResponse<UserResponse> updateUser( @RequestBody UserUpdateRequest request) {
        return APIResponse.<UserResponse>builder()
                .result(userService.updateUser(request))
                .build();
    }

    @PostMapping("/me/avatar")
    public APIResponse<UserResponse> uploadMyAvatar(@RequestParam("file") MultipartFile file) {
        return APIResponse.<UserResponse>builder()
                .result(userService.uploadAvatar(file))
                .build();
    }

    @DeleteMapping("/{userId}")
    String deleteUser(@PathVariable UUID userId){
        userService.deleteUser(userId);
        return "User has been deleted";
    }

    @PostMapping("/create-staff")
    public APIResponse<UserResponse> createStaff(@RequestBody CreateStaffRequest request) {
        return APIResponse.<UserResponse>builder()
                .result(userService.createStaff(request))
                .build();
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) {
        userService.changePassword(request);
        return ResponseEntity.ok("Password updated successfully.");
    }
}
