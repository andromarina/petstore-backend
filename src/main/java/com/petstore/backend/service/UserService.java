package com.petstore.backend.service;

import com.petstore.backend.domain.User;
import com.petstore.backend.repository.UserRepository;
import com.petstore.backend.web.CreateUserRequest;
import com.petstore.backend.web.UserResponse;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<UserResponse> findAll() {
        return userRepository.findAll().stream().map(UserResponse::fromEntity).toList();
    }

    @Transactional
    public UserResponse create(CreateUserRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }
        User user = new User();
        user.setUsername(request.username());
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setPassword(request.password());
        user.setPhone(request.phone());
        user.setUserStatus(request.userStatus());
        User saved = userRepository.save(user);
        return UserResponse.fromEntity(saved);
    }
}
