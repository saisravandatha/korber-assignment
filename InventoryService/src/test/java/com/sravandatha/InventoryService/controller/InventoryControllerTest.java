package com.sravandatha.InventoryService.controller;

import com.sravandatha.InventoryService.model.InventoryResponse;
import com.sravandatha.InventoryService.model.ProductBatchDTO;
import com.sravandatha.InventoryService.model.ProductInventoryDTO;
import com.sravandatha.InventoryService.model.UpdateProductInventoryRequest;
import com.sravandatha.InventoryService.service.InventoryService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InventoryController.class)
class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private InventoryService inventoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getProductsFromInventory_shouldReturnProductInventory() throws Exception {
        // Given & When
        Integer productId = 1;

        List<ProductBatchDTO> batchDTOList = new ArrayList<>();
        batchDTOList.add(new ProductBatchDTO(1, 10, LocalDate.now()));
        ProductInventoryDTO dto = new ProductInventoryDTO(productId, "smartPhone", batchDTOList);
        Mockito.when(inventoryService.getProductsFromInventory(productId))
                .thenReturn(dto);

        // Then
        mockMvc.perform(get("/inventory/{productId}", productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(productId))
                .andExpect(jsonPath("$.productName").value("smartPhone"));
    }

    @Test
    void updateProductInventory_shouldReturnInventoryResponse() throws Exception {
        // Arrange
        UpdateProductInventoryRequest request = new UpdateProductInventoryRequest(1, 5);

        InventoryResponse response = new InventoryResponse("PLACED", "smartPhone", List.of(1));


        Mockito.when(inventoryService.updateProductQuantity(Mockito.any(UpdateProductInventoryRequest.class)))
                .thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/inventory/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("PLACED"))
                .andExpect(jsonPath("$.productName").value("smartPhone"));
    }
}
