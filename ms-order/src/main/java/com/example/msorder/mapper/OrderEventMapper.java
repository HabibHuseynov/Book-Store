package com.example.msorder.mapper;

import com.example.msorder.entity.Order;
import com.example.msorder.entity.OrderItem;
import com.example.msorder.event.model.OrderCancelledEvent;
import com.example.msorder.event.model.OrderCreatedEvent;
import com.example.msorder.event.model.OrderDeliveredEvent;
import com.example.msorder.event.model.OrderErrorEvent;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrderEventMapper {

    public static OrderCreatedEvent buildOrderCreatedEvent(Order order) {
        return new OrderCreatedEvent(
                UUID.randomUUID().toString(),
                order.getOrderNumber(),
                getOrderItems(order),
                order.getCustomer(),
                order.getDeliveryAddress(),
                LocalDateTime.now());
    }

    public static OrderDeliveredEvent buildOrderDeliveredEvent(Order order) {
        return new OrderDeliveredEvent(
                UUID.randomUUID().toString(),
                order.getOrderNumber(),
                getOrderItems(order),
                order.getCustomer(),
                order.getDeliveryAddress(),
                LocalDateTime.now());
    }

    public static OrderCancelledEvent buildOrderCancelledEvent(Order order, String reason) {
        return new OrderCancelledEvent(
                UUID.randomUUID().toString(),
                order.getOrderNumber(),
                getOrderItems(order),
                order.getCustomer(),
                order.getDeliveryAddress(),
                reason,
                LocalDateTime.now());
    }

    public static OrderErrorEvent buildOrderErrorEvent(Order order, String reason) {
        return new OrderErrorEvent(
                UUID.randomUUID().toString(),
                order.getOrderNumber(),
                getOrderItems(order),
                order.getCustomer(),
                order.getDeliveryAddress(),
                reason,
                LocalDateTime.now());
    }

    private static Set<OrderItem> getOrderItems(Order order) {
        return order.getItems().stream()
                .map(item -> new OrderItem(item.getCode(),
                        item.getName(),
                        item.getPrice(),
                        item.getQuantity()))
                .collect(Collectors.toSet());
    }
}
