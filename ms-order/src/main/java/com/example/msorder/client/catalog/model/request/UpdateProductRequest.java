package com.example.msorder.client.catalog.model.request;

import java.math.BigDecimal;

public record UpdateProductRequest(
        String name,
        String description,
        String imageUrl,
        BigDecimal price
) {}
