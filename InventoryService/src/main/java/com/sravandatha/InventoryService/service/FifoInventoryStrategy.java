package com.sravandatha.InventoryService.service;

import com.sravandatha.InventoryService.entity.ProductInventoryEntity;
import com.sravandatha.InventoryService.exception.InventoryServiceException;
import com.sravandatha.InventoryService.model.InventoryResponse;
import com.sravandatha.InventoryService.repository.InventoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class FifoInventoryStrategy implements InventoryAllocationStrategy {

    private final InventoryRepository inventoryRepository;

    public FifoInventoryStrategy(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    @Transactional
    public InventoryResponse allocateInventory(Integer productId, int requestedQuantity) {

        List<ProductInventoryEntity> inventoryList =
                inventoryRepository.findProductsByIdAndOrderByExpiryDate(productId);

        int availableQuantity = inventoryList.stream()
                .mapToInt(ProductInventoryEntity::getQuantity)
                .sum();

        if (requestedQuantity > availableQuantity) {
            throw new InventoryServiceException("Insufficient inventory");
        }

        int remainingQuantity = requestedQuantity;
        List<Integer> reservedBatchIds = new ArrayList<>();

        for (ProductInventoryEntity inventory : inventoryList) {

            if (remainingQuantity <= 0) {
                break;
            }

            int batchQty = inventory.getQuantity();
            reservedBatchIds.add(inventory.getBatchId());

            if (batchQty <= remainingQuantity) {
                remainingQuantity -= batchQty;
                inventoryRepository.delete(inventory);
            } else {
                inventory.setQuantity(batchQty - remainingQuantity);
                inventoryRepository.save(inventory);
                remainingQuantity = 0;
            }
        }

        return InventoryResponse.builder()
                .message("PLACED")
                .productName(inventoryList.getFirst().getProductName())
                .batchIds(reservedBatchIds)
                .build();
    }
}
