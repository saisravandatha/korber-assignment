package com.sravandatha.OrderService.model;

import lombok.Builder;

@Builder
public record CreateOrderRequest(Integer productId, Integer quantity) {
}
