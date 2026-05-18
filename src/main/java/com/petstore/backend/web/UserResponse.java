package com.petstore.backend.web;

import com.petstore.backend.domain.User;

public record UserResponse(
        long id,
        String username,
        String firstName,
        String lastName,
        String email,
        String password,
        String phone,
        int userStatus
) {

    public static UserResponse fromEntity(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getPhone() != null ? user.getPhone() : "",
                user.getUserStatus()
        );
    }
}
