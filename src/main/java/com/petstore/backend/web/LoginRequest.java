package com.petstore.backend.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotBlank @Size(max = 255) String username,
        @NotBlank @Size(max = 255) String password
) {
}
