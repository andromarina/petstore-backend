package com.petstore.backend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "petstore.jwt")
public record JwtProperties(String secret, long expirationMs) {
}
