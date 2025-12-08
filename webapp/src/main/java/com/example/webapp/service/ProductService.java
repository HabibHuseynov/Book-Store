package com.example.webapp.service;

import com.example.webapp.client.catalog.CatalogServiceClient;
import com.example.webapp.client.catalog.model.request.CreateProductRequest;
import com.example.webapp.client.catalog.model.request.ProductSearchCriteria;
import com.example.webapp.client.catalog.model.request.UpdateProductRequest;
import com.example.webapp.client.catalog.model.response.PagedResult;
import com.example.webapp.client.catalog.model.response.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final CatalogServiceClient productClient;

    public PagedResult<Product> getProducts(int page) {
        int backendPage = Math.max(0, page - 1);
        return productClient.getProducts(backendPage);
    }

    public Product getProductByCode(String code) {
        ResponseEntity<Product> response = productClient.getProductByCode(code);
        return response.getBody();
    }

    public Product createProduct(CreateProductRequest request) {
        ResponseEntity<Product> response = productClient.createProduct(request);
        return response.getBody();
    }

    public Product updateProduct(String code, UpdateProductRequest request) {
        ResponseEntity<Product> response = productClient.updateProduct(code, request);
        return response.getBody();
    }

    public void deleteProduct(String code) {
        productClient.deleteProduct(code);
    }

    public PagedResult<Product> searchProducts(ProductSearchCriteria criteria, int page) {
        int backendPage = Math.max(0, page - 1);
        String code = criteria != null ? criteria.code() : null;
        String name = criteria != null ? criteria.name() : null;
        BigDecimal minPrice = criteria != null ? criteria.minPrice() : null;
        BigDecimal maxPrice = criteria != null ? criteria.maxPrice() : null;
        return productClient.searchProducts(backendPage, code, name, minPrice, maxPrice);
    }
}
