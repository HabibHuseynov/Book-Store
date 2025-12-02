package com.example.catalog.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResponse(
        Long id,
        String code,
        String name,
        String description,
        String imageUrl,
        BigDecimal price,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}

