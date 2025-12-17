package com.example.msorder.service;

import com.example.msorder.client.catalog.CatalogServiceClient;
import com.example.msorder.client.catalog.model.response.Product;
import com.example.msorder.dto.CreateOrderRequest;
import com.example.msorder.entity.OrderItem;
import com.example.msorder.error.InvalidOrderException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Slf4j
@RequiredArgsConstructor
class OrderValidator {

    private final CatalogServiceClient client;


    void validate(CreateOrderRequest request) {
        Set<OrderItem> items = request.items();
        for (OrderItem item : items) {
            Product product = client.getProductByCode(item.getCode())
                    .orElseThrow(() -> new InvalidOrderException("Invalid Product code:" + item.getCode()));
            if (item.getPrice().compareTo(product.price()) != 0) {
                log.error(
                        "Product price not matching. Actual price:{}, received price:{}",
                        product.price(),
                        item.getPrice());
                throw new InvalidOrderException("Product price not matching");
            }
        }
    }
}
