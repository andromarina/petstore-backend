package com.petstore.backend.config;

import com.petstore.backend.domain.Role;
import com.petstore.backend.domain.User;
import com.petstore.backend.repository.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class AdminUserInitializer implements ApplicationRunner {

    private static final int DEFAULT_ADMIN_USER_STATUS = 1;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AdminProperties adminProperties;

    public AdminUserInitializer(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AdminProperties adminProperties
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.adminProperties = adminProperties;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (userRepository.existsByUsername(adminProperties.username())) {
            return;
        }

        User admin = new User();
        admin.setUsername(adminProperties.username());
        admin.setFirstName("Admin");
        admin.setLastName("User");
        admin.setEmail("admin@petstore.local");
        admin.setPassword(passwordEncoder.encode(adminProperties.password()));
        admin.setPhone("");
        admin.setUserStatus(DEFAULT_ADMIN_USER_STATUS);
        admin.setRole(Role.ADMIN);
        userRepository.save(admin);
    }
}
