package com.example.msorder.mapper;

import com.example.msorder.dto.CreateOrderRequest;
import com.example.msorder.dto.OrderDTO;
import com.example.msorder.entity.Order;
import com.example.msorder.entity.OrderItem;
import com.example.msorder.enums.OrderStatus;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrderMapper {
    public static Order convertToEntity(CreateOrderRequest request) {
        Order newOrder = new Order();
        newOrder.setOrderNumber(UUID.randomUUID().toString());
        newOrder.setStatus(OrderStatus.NEW);
        newOrder.setCustomer(request.customer());
        newOrder.setDeliveryAddress(request.deliveryAddress());
        Set<OrderItem> orderItems = new HashSet<>();
        for (OrderItem item : request.items()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setCode(item.getCode());
            orderItem.setName(item.getName());
            orderItem.setPrice(item.getPrice());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setOrder(newOrder);
            orderItems.add(orderItem);
        }
        newOrder.setItems(orderItems);
        return newOrder;
    }

    public static OrderDTO convertToDTO(Order order) {
        Set<OrderItem> orderItems = order.getItems().stream()
                .map(item -> new OrderItem(item.getCode(), item.getName(), item.getPrice(), item.getQuantity()))
                .collect(Collectors.toSet());

        return new OrderDTO(
                order.getOrderNumber(),
                order.getUserName(),
                orderItems,
                order.getCustomer(),
                order.getDeliveryAddress(),
                order.getStatus(),
                order.getComments(),
                order.getCreatedAt());
    }
}
