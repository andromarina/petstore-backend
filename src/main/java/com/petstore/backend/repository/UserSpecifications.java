package com.petstore.backend.repository;

import com.petstore.backend.domain.User;
import org.springframework.data.jpa.domain.Specification;

public final class UserSpecifications {

    private UserSpecifications() {
    }

    public static Specification<User> withFilters(
            String username,
            String email,
            String firstName,
            String lastName
    ) {
        return (root, query, criteriaBuilder) -> {
            var predicates = criteriaBuilder.conjunction();

            if (username != null && !username.isBlank()) {
                predicates = criteriaBuilder.and(
                        predicates,
                        criteriaBuilder.equal(
                                criteriaBuilder.lower(root.get("username")),
                                username.trim().toLowerCase()
                        )
                );
            }
            if (email != null && !email.isBlank()) {
                predicates = criteriaBuilder.and(
                        predicates,
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("email")),
                                "%" + email.trim().toLowerCase() + "%"
                        )
                );
            }
            if (firstName != null && !firstName.isBlank()) {
                predicates = criteriaBuilder.and(
                        predicates,
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("firstName")),
                                "%" + firstName.trim().toLowerCase() + "%"
                        )
                );
            }
            if (lastName != null && !lastName.isBlank()) {
                predicates = criteriaBuilder.and(
                        predicates,
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("lastName")),
                                "%" + lastName.trim().toLowerCase() + "%"
                        )
                );
            }

            return predicates;
        };
    }
}
