package com.example.beprojectweb.controller;

import com.example.beprojectweb.dto.response.APIResponse;
import com.example.beprojectweb.service.CloudinaryService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/uploads")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UploadController {

    @Autowired
    CloudinaryService cloudinaryService;

    @PostMapping("/image")
    public APIResponse<String> uploadImage(@RequestParam("file") MultipartFile file) {
        String url = cloudinaryService.uploadImage(file);
        return APIResponse.<String>builder()
                .result(url)
                .build();
    }
}
