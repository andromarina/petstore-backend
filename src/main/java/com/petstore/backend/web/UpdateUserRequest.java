package com.petstore.backend.web;

import com.petstore.backend.domain.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateUserRequest(
        @NotBlank @Size(max = 255) String username,
        @NotBlank @Size(max = 255) String firstName,
        @NotBlank @Size(max = 255) String lastName,
        @NotBlank @Email @Size(max = 255) String email,
        @Size(max = 255) String password,
        @Size(max = 64) String phone,
        @NotNull Integer userStatus,
        Role role
) {

    public UpdateUserRequest {
        phone = phone != null ? phone : "";
        password = password != null ? password : "";
    }
}
