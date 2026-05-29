package com.petstore.backend.web;

import com.petstore.backend.domain.Role;
import com.petstore.backend.domain.User;

public record LoginResponse(
        String accessToken,
        String tokenType,
        long id,
        String username,
        String firstName,
        String lastName,
        String email,
        Role role
) {

    public static LoginResponse fromEntity(User user, String accessToken) {
        return new LoginResponse(
                accessToken,
                "Bearer",
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole()
        );
    }
}
