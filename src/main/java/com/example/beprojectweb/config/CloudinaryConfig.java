package com.example.beprojectweb.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "di2zkzssv",     // ✅ sửa tên cloud
                "api_key", "686364945891792",           // ✅ sửa key
                "api_secret", "3vFPoJveoawTZXvgVq2-PgViLF0",     // ✅ sửa secret
                "secure", true
        ));
    }
}
