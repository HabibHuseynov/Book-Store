package com.example.catalog.service;


import com.example.catalog.dto.*;
import com.example.catalog.entity.Product;
import com.example.catalog.error.ProductAlreadyExistsException;
import com.example.catalog.error.ProductNotFoundException;
import com.example.catalog.mapper.ProductMapper;
import com.example.catalog.repository.ProductRepository;
import com.example.catalog.service.specification.ProductSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Transactional
    public ProductResponse createProduct(CreateProductRequest request) {
        if (productRepository.existsByCode(request.code())) {
            throw new ProductAlreadyExistsException(request.code());
        }
        Product product = productMapper.toEntity(request);
        Product savedProduct = productRepository.save(product);
        return productMapper.toResponse(savedProduct);
    }

    @Transactional
    public ProductResponse updateProduct(String code, UpdateProductRequest request) {
        Product product = productRepository.findByCode(code)
                .orElseThrow(() -> new ProductNotFoundException(code));

        productMapper.updateEntityFromRequest(request, product);
        Product updatedProduct = productRepository.save(product);
        return productMapper.toResponse(updatedProduct);
    }

    public ProductResponse getProductByCode(String code) {
        return productRepository.findByCode(code)
                .map(productMapper::toResponse)
                .orElseThrow(() -> new ProductNotFoundException(code));
    }

    @Transactional
    public void deleteProduct(String code) {
        Product product = productRepository.findByCode(code)
                .orElseThrow(() -> new ProductNotFoundException(code));
        productRepository.delete(product);
    }

    public PagedResult<ProductResponse> getProducts(ProductSearchCriteria criteria, Pageable pageable) {
        Page<ProductResponse> resultPage = productRepository.findAll(
                ProductSpecification.search(criteria), pageable)
                .map(productMapper::toResponse);

        return new PagedResult<>(
                resultPage.getContent(),
                resultPage.getTotalElements(),
                resultPage.getNumber() + 1,
                resultPage.getTotalPages(),
                resultPage.isFirst(),
                resultPage.isLast(),
                resultPage.hasNext(),
                resultPage.hasPrevious()
        );
    }
}
