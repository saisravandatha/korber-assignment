package com.sravandatha.OrderService.model;

import lombok.Builder;

import java.util.List;

@Builder
public record InventoryResponse(String message, String productName, List<Integer> batchIds) {
}
