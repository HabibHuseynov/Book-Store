package com.example.msorder.config;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "orders")
public record RabbitMqProperties(

        @NotBlank
        String orderEventsExchange,

        @NotBlank
        String newOrdersQueue,

        @NotBlank
        String deliveredOrdersQueue,

        @NotBlank
        String cancelledOrdersQueue,

        @NotBlank
        String errorOrdersQueue
) {
}
