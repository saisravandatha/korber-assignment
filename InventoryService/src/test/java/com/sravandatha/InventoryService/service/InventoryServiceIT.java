package com.sravandatha.InventoryService.service;

import com.sravandatha.InventoryService.model.ProductInventoryDTO;
import com.sravandatha.InventoryService.repository.InventoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class InventoryServiceIT {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Test
    void getProductsFromInventory_shouldReadFromH2Database() {
        ProductInventoryDTO result =
                inventoryService.getProductsFromInventory(1001);

        assertNotNull(result);
        assertEquals(1001, result.productId());
        assertEquals("Laptop", result.productName());
    }
}
