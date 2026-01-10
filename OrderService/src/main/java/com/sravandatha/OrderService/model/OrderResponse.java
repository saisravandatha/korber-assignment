package com.sravandatha.OrderService.model;

import lombok.Builder;

import java.util.List;

@Builder
public record OrderResponse(Integer orderId, Integer productId, String productName,
                            Integer quantity, String status, List<Integer> reservedFromBatchIds,
                            String message) {
}
