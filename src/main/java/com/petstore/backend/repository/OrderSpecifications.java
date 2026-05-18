package com.petstore.backend.repository;

import com.petstore.backend.domain.Order;
import org.springframework.data.jpa.domain.Specification;

public final class OrderSpecifications {

    private OrderSpecifications() {
    }

    public static Specification<Order> withFilters(Long petId, String status, Boolean complete) {
        return (root, query, criteriaBuilder) -> {
            var predicates = criteriaBuilder.conjunction();

            if (petId != null) {
                predicates = criteriaBuilder.and(
                        predicates,
                        criteriaBuilder.equal(root.get("petId"), petId)
                );
            }
            if (status != null && !status.isBlank()) {
                predicates = criteriaBuilder.and(
                        predicates,
                        criteriaBuilder.equal(
                                criteriaBuilder.lower(root.get("status")),
                                status.trim().toLowerCase()
                        )
                );
            }
            if (complete != null) {
                predicates = criteriaBuilder.and(
                        predicates,
                        criteriaBuilder.equal(root.get("complete"), complete)
                );
            }

            return predicates;
        };
    }
}
