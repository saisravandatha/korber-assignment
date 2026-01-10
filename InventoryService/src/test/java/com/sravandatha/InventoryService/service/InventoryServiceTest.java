package com.sravandatha.InventoryService.service;

import com.sravandatha.InventoryService.entity.ProductInventoryEntity;
import com.sravandatha.InventoryService.model.InventoryResponse;
import com.sravandatha.InventoryService.model.ProductInventoryDTO;
import com.sravandatha.InventoryService.model.UpdateProductInventoryRequest;
import com.sravandatha.InventoryService.repository.InventoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryServiceTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @Mock
    private InventoryStrategyFactory strategyFactory;

    @Mock
    private InventoryAllocationStrategy allocationStrategy;

    @InjectMocks
    private InventoryService inventoryService;

    @Test
    void getProductsFromInventory_shouldReturnProductInventoryDTO() {
        // Arrange
        Integer productId = 1;

        ProductInventoryEntity entity1 = new ProductInventoryEntity();
        entity1.setProductId(productId);
        entity1.setProductName("Test Product");
        entity1.setBatchId(1);
        entity1.setQuantity(10);
        entity1.setExpiryDate(LocalDate.now().plusDays(10));

        ProductInventoryEntity entity2 = new ProductInventoryEntity();
        entity2.setProductId(productId);
        entity2.setProductName("Test Product");
        entity2.setBatchId(2);
        entity2.setQuantity(20);
        entity2.setExpiryDate(LocalDate.now().plusDays(20));

        when(inventoryRepository.findProductsByIdAndOrderByExpiryDate(productId))
                .thenReturn(List.of(entity1, entity2));

        // Act
        ProductInventoryDTO result = inventoryService.getProductsFromInventory(productId);

        // Assert
        assertNotNull(result);
        assertEquals(productId, result.productId());
        assertEquals("Test Product", result.productName());
        assertEquals(2, result.batches().size());

        assertEquals(1, result.batches().get(0).batchId());
        assertEquals(10, result.batches().get(0).quantity());

        verify(inventoryRepository, times(1))
                .findProductsByIdAndOrderByExpiryDate(productId);
    }

    @Test
    void updateProductQuantity_shouldUseFifoStrategyAndReturnResponse() {
        // Arrange
        UpdateProductInventoryRequest request =
                new UpdateProductInventoryRequest(1, 5);

        InventoryResponse response = new InventoryResponse("PLACED","smartPhone",List.of(1));

        when(strategyFactory.getStrategy(InventoryStrategyType.FIFO))
                .thenReturn(allocationStrategy);

        when(allocationStrategy.allocateInventory(5, 1))
                .thenReturn(response);

        // Act
        InventoryResponse result = inventoryService.updateProductQuantity(request);

        // Assert
        assertNotNull(result);
        assertEquals("PLACED", result.message());

        verify(strategyFactory, times(1))
                .getStrategy(InventoryStrategyType.FIFO);

        verify(allocationStrategy, times(1))
                .allocateInventory(5, 1);
    }
}
