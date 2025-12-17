package com.example.msorder.client.catalog.model.request;

import java.math.BigDecimal;

public record ProductSearchCriteria(
        String code,
        String name,
        BigDecimal minPrice,
        BigDecimal maxPrice
) {}
