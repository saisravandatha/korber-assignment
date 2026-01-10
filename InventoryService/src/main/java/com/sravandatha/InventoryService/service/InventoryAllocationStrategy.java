package com.sravandatha.InventoryService.service;

import com.sravandatha.InventoryService.model.InventoryResponse;

import java.util.List;

public interface InventoryAllocationStrategy {

    InventoryResponse allocateInventory(
            Integer productId,
            int requestedQuantity
    );
}
