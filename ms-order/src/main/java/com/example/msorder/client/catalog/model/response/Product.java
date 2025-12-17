package com.example.msorder.client.catalog.model.response;

import java.math.BigDecimal;

public record Product(Long id,
                      String code,
                      String name,
                      String description,
                      String imageUrl,
                      BigDecimal price) {}
