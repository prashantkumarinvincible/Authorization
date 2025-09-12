package com.example.entitlements.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {
    // Default cache manager from Spring Boot will be used.
    // For distributed caching, provide a RedisCacheManager bean here.
}
