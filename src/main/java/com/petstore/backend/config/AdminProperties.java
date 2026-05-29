package com.petstore.backend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "petstore.admin")
public record AdminProperties(String username, String password) {
}
