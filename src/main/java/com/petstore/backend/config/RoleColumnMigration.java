package com.petstore.backend.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@Order(0)
public class RoleColumnMigration implements ApplicationRunner {

    private final JdbcTemplate jdbcTemplate;
    private final AdminProperties adminProperties;

    public RoleColumnMigration(JdbcTemplate jdbcTemplate, AdminProperties adminProperties) {
        this.jdbcTemplate = jdbcTemplate;
        this.adminProperties = adminProperties;
    }

    @Override
    public void run(ApplicationArguments args) {
        jdbcTemplate.execute(
                "ALTER TABLE users ADD COLUMN IF NOT EXISTS user_role VARCHAR(16) NOT NULL DEFAULT 'USER'"
        );

        jdbcTemplate.update(
                "UPDATE users SET user_role = 'ADMIN' WHERE LOWER(username) = LOWER(?) AND user_role <> 'ADMIN'",
                adminProperties.username()
        );
    }
}
