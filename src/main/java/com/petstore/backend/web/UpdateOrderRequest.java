package com.petstore.backend.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.time.Instant;

public record UpdateOrderRequest(
        @NotNull @Positive Long petId,
        @NotNull @Positive Integer quantity,
        @NotNull Instant shipDate,
        @NotBlank @Size(max = 64) String status,
        @NotNull Boolean complete
) {
}
