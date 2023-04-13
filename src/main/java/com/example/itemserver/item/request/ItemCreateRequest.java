package com.example.itemserver.item.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ItemCreateRequest {
    @NotNull(message = "Name is missing")
    @NotBlank(message = "Name is required")
    private String name;

    @DecimalMin(value = "0.0", message = "Value must be greater than 0")
    private BigDecimal regularPrice;

    @DecimalMin(value = "0.0", message = "Value must be greater than 0")
    private BigDecimal discountPrice;

    private String description;

}