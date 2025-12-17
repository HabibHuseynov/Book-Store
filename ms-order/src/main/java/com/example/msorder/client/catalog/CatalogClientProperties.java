package com.example.msorder.client.catalog;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "catalog.client")
public record CatalogClientProperties(
        String baseUrl
) {}