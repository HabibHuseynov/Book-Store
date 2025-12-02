package com.example.catalog.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateProductRequest(
        @NotBlank(message = "Product code is required")
        String code,

        @NotBlank(message = "Product name is required")
        String name,

        String description,
        String imageUrl,

        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.1", message = "Price must be greater than 0")
        BigDecimal price
) {}
