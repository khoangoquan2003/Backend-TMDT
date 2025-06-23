package com.example.beprojectweb;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BeProjectWebApplication {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure().load(); // từ thư viện io.github.cdimascio
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
        SpringApplication.run(BeProjectWebApplication.class, args);
    }

}
