package com.example.webapp.client.catalog.model.request;

import java.math.BigDecimal;

public record CreateProductRequest(
        String code,
        String name,
        String description,
        String imageUrl,
        BigDecimal price
) {}
