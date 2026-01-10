package com.sravandatha.InventoryService.service;

import com.sravandatha.InventoryService.entity.ProductInventoryEntity;
import com.sravandatha.InventoryService.exception.InventoryServiceException;
import com.sravandatha.InventoryService.model.InventoryResponse;
import com.sravandatha.InventoryService.model.ProductBatchDTO;
import com.sravandatha.InventoryService.model.ProductInventoryDTO;
import com.sravandatha.InventoryService.model.UpdateProductInventoryRequest;
import com.sravandatha.InventoryService.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryStrategyFactory strategyFactory;

    public ProductInventoryDTO getProductsFromInventory(Integer productId) {
        List<ProductInventoryEntity> inventoryEntities = inventoryRepository.findProductsByIdAndOrderByExpiryDate(productId);
        List<ProductBatchDTO> batchList = new ArrayList<>();

        for (ProductInventoryEntity entity : inventoryEntities){
            batchList.add(ProductBatchDTO.builder()
                    .batchId(entity.getBatchId())
                    .expiryDate(entity.getExpiryDate())
                    .quantity(entity.getQuantity())
                    .build());
        }

        return ProductInventoryDTO.builder()
                .productName(inventoryEntities.getFirst().getProductName())
                .productId(productId)
                .batches(batchList)
                .build();
    }

    public InventoryResponse updateProductQuantity(UpdateProductInventoryRequest request) {
        InventoryAllocationStrategy strategy =
                strategyFactory.getStrategy(InventoryStrategyType.FIFO);

        return strategy.allocateInventory(request.quantity(), request.productId());
    }
}
