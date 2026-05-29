package com.petstore.backend.service;

import com.petstore.backend.domain.Role;
import com.petstore.backend.domain.User;
import com.petstore.backend.repository.UserRepository;
import com.petstore.backend.repository.UserSpecifications;
import com.petstore.backend.web.CreateUserRequest;
import com.petstore.backend.web.RegisterRequest;
import com.petstore.backend.web.RegisterResponse;
import com.petstore.backend.web.UpdateUserRequest;
import com.petstore.backend.web.UserResponse;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    private static final int DEFAULT_REGISTERED_USER_STATUS = 1;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public List<UserResponse> findAll() {
        return userRepository.findAll().stream().map(UserResponse::fromEntity).toList();
    }

    @Transactional(readOnly = true)
    public UserResponse findById(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return UserResponse.fromEntity(user);
    }

    @Transactional(readOnly = true)
    public UserResponse findByUsername(String username) {
        if (username == null || username.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username is required");
        }
        User user = userRepository.findByUsernameIgnoreCase(username.trim())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return UserResponse.fromEntity(user);
    }

    @Transactional(readOnly = true)
    public List<UserResponse> search(String username, String email, String firstName, String lastName) {
        if (!hasSearchCriteria(username, email, firstName, lastName)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "At least one search parameter is required: username, email, firstName, lastName"
            );
        }
        return userRepository.findAll(UserSpecifications.withFilters(username, email, firstName, lastName))
                .stream()
                .map(UserResponse::fromEntity)
                .toList();
    }

    @Transactional
    public RegisterResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }
        User user = new User();
        user.setUsername(request.username());
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setPhone(request.phone());
        user.setUserStatus(DEFAULT_REGISTERED_USER_STATUS);
        user.setRole(Role.USER);
        User saved = userRepository.save(user);
        return RegisterResponse.fromEntity(saved);
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
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setPhone(request.phone());
        user.setUserStatus(request.userStatus());
        user.setRole(request.role() != null ? request.role() : Role.USER);
        User saved = userRepository.save(user);
        return UserResponse.fromEntity(saved);
    }

    @Transactional
    public void delete(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        userRepository.delete(user);
    }

    @Transactional
    public UserResponse update(long id, UpdateUserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        if (userRepository.existsByUsernameAndIdNot(request.username(), id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }
        user.setUsername(request.username());
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        if (!request.password().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.password()));
        }
        user.setPhone(request.phone());
        user.setUserStatus(request.userStatus());
        if (request.role() != null) {
            user.setRole(request.role());
        }
        User saved = userRepository.save(user);
        return UserResponse.fromEntity(saved);
    }

    private boolean hasSearchCriteria(String username, String email, String firstName, String lastName) {
        return isNotBlank(username) || isNotBlank(email) || isNotBlank(firstName) || isNotBlank(lastName);
    }

    private boolean isNotBlank(String value) {
        return value != null && !value.isBlank();
    }
}
