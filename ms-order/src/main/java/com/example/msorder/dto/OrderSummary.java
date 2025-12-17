package com.example.msorder.dto;

import com.example.msorder.enums.OrderStatus;

public record OrderSummary(String orderNumber, OrderStatus status) {}
