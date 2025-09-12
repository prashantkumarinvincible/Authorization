package com.example.entitlements;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class EntitlementLibApplication {
    public static void main(String[] args) {
        SpringApplication.run(EntitlementLibApplication.class, args);
    }
}
