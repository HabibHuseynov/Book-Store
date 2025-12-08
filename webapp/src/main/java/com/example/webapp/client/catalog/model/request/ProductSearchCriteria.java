package com.example.webapp.client.catalog.model.request;

import java.math.BigDecimal;

public record ProductSearchCriteria(
        String code,
        String name,
        BigDecimal minPrice,
        BigDecimal maxPrice
) {}
