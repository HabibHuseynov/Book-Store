package com.example.msorder.event.model;

import com.example.msorder.dto.Address;
import com.example.msorder.dto.Customer;
import com.example.msorder.entity.OrderItem;

import java.time.LocalDateTime;
import java.util.Set;

public record OrderDeliveredEvent(
        String eventId,
        String orderNumber,
        Set<OrderItem> items,
        Customer customer,
        Address deliveryAddress,
        LocalDateTime createdAt) {}
