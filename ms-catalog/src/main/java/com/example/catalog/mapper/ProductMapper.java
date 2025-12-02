package com.example.catalog.mapper;

import com.example.catalog.dto.CreateProductRequest;
import com.example.catalog.dto.ProductResponse;
import com.example.catalog.dto.UpdateProductRequest;
import com.example.catalog.entity.Product;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductResponse toResponse(Product product);

    Product toEntity(CreateProductRequest request);

    // Updates existing entity. Ignores null values in the request.
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(UpdateProductRequest request, @MappingTarget Product product);
}