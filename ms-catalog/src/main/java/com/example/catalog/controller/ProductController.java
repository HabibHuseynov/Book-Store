package com.example.catalog.controller;

import com.example.catalog.dto.CreateProductRequest;
import com.example.catalog.dto.PagedResult;
import com.example.catalog.dto.ProductResponse;
import com.example.catalog.dto.ProductSearchCriteria;
import com.example.catalog.dto.UpdateProductRequest;
import com.example.catalog.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody CreateProductRequest request) {
        ProductResponse response = productService.createProduct(request);
        URI location = URI.create("/api/v1/products/" + response.code());
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{code}")
    public ResponseEntity<ProductResponse> update(@PathVariable String code,
                                                  @Valid @RequestBody UpdateProductRequest request) {
        ProductResponse response = productService.updateProduct(code, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{code}")
    public ResponseEntity<ProductResponse> getByCode(@PathVariable String code) {
        ProductResponse response = productService.getProductByCode(code);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{code}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String code) {
        productService.deleteProduct(code);
    }

    @GetMapping
    public ResponseEntity<PagedResult<ProductResponse>> search(@ModelAttribute ProductSearchCriteria criteria,
                                                               Pageable pageable) {
        PagedResult<ProductResponse> result = productService.getProducts(criteria, pageable);
        return ResponseEntity.ok(result);
    }
}
