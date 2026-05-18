package com.petstore.backend.web;

import com.petstore.backend.domain.Order;
import java.time.Instant;

public record OrderResponse(
        long id,
        long petId,
        int quantity,
        Instant shipDate,
        String status,
        boolean complete
) {

    public static OrderResponse fromEntity(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getPetId(),
                order.getQuantity(),
                order.getShipDate(),
                order.getStatus(),
                order.getComplete()
        );
    }
}
