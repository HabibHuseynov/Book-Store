package com.example.webapp.client.catalog;

import com.example.webapp.client.catalog.model.request.CreateProductRequest;
import com.example.webapp.client.catalog.model.request.UpdateProductRequest;
import com.example.webapp.client.catalog.model.response.PagedResult;
import com.example.webapp.client.catalog.model.response.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.web.service.annotation.PutExchange;

import java.math.BigDecimal;

public interface CatalogServiceClient {

    @GetExchange("/api/v1/products")
    PagedResult<Product> getProducts(@RequestParam("page") int page);

    @GetExchange("/api/v1/products")
    PagedResult<Product> searchProducts(@RequestParam("page") int page,
                                        @RequestParam(value = "code", required = false) String code,
                                        @RequestParam(value = "name", required = false) String name,
                                        @RequestParam(value = "minPrice", required = false) BigDecimal minPrice,
                                        @RequestParam(value = "maxPrice", required = false) BigDecimal maxPrice);

    @GetExchange("/api/v1/products/{code}")
    ResponseEntity<Product> getProductByCode(@PathVariable String code);

    @PostExchange("/api/v1/products")
    ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest request);

    @PutExchange("/api/v1/products/{code}")
    ResponseEntity<Product> updateProduct(@PathVariable String code, @RequestBody UpdateProductRequest request);

    @DeleteExchange("/api/v1/products/{code}")
    ResponseEntity<Void> deleteProduct(@PathVariable String code);
}
