package com.example.beprojectweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BeProjectWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeProjectWebApplication.class, args);
    }

}
