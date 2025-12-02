package com.example.catalog.error;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String code) {
        super("Product with code " + code + " not found");
    }
}

