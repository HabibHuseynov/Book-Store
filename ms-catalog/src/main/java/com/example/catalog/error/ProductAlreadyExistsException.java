package com.example.catalog.error;

public class ProductAlreadyExistsException extends RuntimeException {
    public ProductAlreadyExistsException(String code) {
        super("Product with code " + code + " already exists");
    }
}
