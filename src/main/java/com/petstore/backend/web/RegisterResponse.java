package com.petstore.backend.web;

import com.petstore.backend.domain.User;

public record RegisterResponse(
        long id,
        String username,
        String firstName,
        String lastName,
        String email,
        String phone,
        int userStatus
) {

    public static RegisterResponse fromEntity(User user) {
        return new RegisterResponse(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhone() != null ? user.getPhone() : "",
                user.getUserStatus()
        );
    }
}
