package com.petstore.backend.web;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank @Size(max = 255) String username,
        @NotBlank @Size(max = 255) String firstName,
        @NotBlank @Size(max = 255) String lastName,
        @NotBlank @Email @Size(max = 255) String email,
        @NotBlank @Size(min = 6, max = 255) String password,
        @Size(max = 64) String phone
) {

    public RegisterRequest {
        phone = phone != null ? phone : "";
    }
}
