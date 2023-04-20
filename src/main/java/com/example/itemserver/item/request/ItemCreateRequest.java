package com.example.itemserver.item.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ItemCreateRequest(
        @NotNull(message = "Market ID is missing")
        Long marketId,

        @NotNull(message = "Name is missing")
        @NotBlank(message = "Name is required")
        String name,

        @DecimalMin(value = "0.0", message = "Value must be greater than 0")
        BigDecimal regularPrice,

        @DecimalMin(value = "0.0", message = "Value must be greater than 0")
        BigDecimal discountPrice,

        String description
) {
}