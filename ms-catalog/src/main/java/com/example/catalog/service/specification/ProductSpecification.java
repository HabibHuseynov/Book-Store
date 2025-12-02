package com.example.catalog.service.specification;

import com.example.catalog.dto.ProductSearchCriteria;
import com.example.catalog.entity.Product;
import org.springframework.data.jpa.domain.Specification;

import java.util.Objects;

import static com.example.catalog.util.SpecificationUtil.equal;
import static com.example.catalog.util.SpecificationUtil.like;
import static com.example.catalog.util.SpecificationUtil.range;

public class ProductSpecification {

    private ProductSpecification() {}

    public static Specification<Product> search(ProductSearchCriteria criteria) {
        if (Objects.nonNull(criteria)) {
            return Specification.<Product>where(equal("code", criteria.code()))
                    .and(like("name", criteria.name()))
                    .and(range("price",criteria.minPrice(), criteria.maxPrice()));
        }
        return null;
    }
}
