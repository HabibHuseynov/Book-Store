package com.example.catalog.dto;

import java.math.BigDecimal;

public record ProductSearchCriteria(
        String code,
        String name,
        BigDecimal minPrice,
        BigDecimal maxPrice
) {}
