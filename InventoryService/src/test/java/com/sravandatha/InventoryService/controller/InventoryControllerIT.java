package com.sravandatha.InventoryService.controller;


import com.sravandatha.InventoryService.model.InventoryResponse;
import com.sravandatha.InventoryService.model.UpdateProductInventoryRequest;
import com.sravandatha.InventoryService.service.InventoryAllocationStrategy;
import com.sravandatha.InventoryService.service.InventoryStrategyFactory;
import com.sravandatha.InventoryService.service.InventoryStrategyType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class InventoryControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * We mock strategy layer to avoid complex allocation logic
     */
    @MockitoBean
    private InventoryStrategyFactory strategyFactory;

    @MockitoBean
    private InventoryAllocationStrategy allocationStrategy;

    @Test
    void getProductsFromInventory_shouldReturnInventoryFromH2() throws Exception {
        mockMvc.perform(get("/inventory/{productId}", 1001))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(1001))
                .andExpect(jsonPath("$.productName").value("Laptop"))
                .andExpect(jsonPath("$.batches.length()").value(1))
                .andExpect(jsonPath("$.batches[0].batchId").value(1));
    }

    @Test
    void updateProductInventory_shouldAllocateInventory() throws Exception {
        UpdateProductInventoryRequest request =
                new UpdateProductInventoryRequest(1002, 5);

        InventoryResponse response = new InventoryResponse("PLACED","Smartphone" , List.of(1));
        when(strategyFactory.getStrategy(InventoryStrategyType.FIFO))
                .thenReturn(allocationStrategy);

        when(allocationStrategy.allocateInventory(1002, 5))
                .thenReturn(response);

        mockMvc.perform(post("/inventory/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }
}
