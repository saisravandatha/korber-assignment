package com.sravandatha.InventoryService.controller;

import com.sravandatha.InventoryService.model.InventoryResponse;
import com.sravandatha.InventoryService.model.ProductInventoryDTO;
import com.sravandatha.InventoryService.model.UpdateProductInventoryRequest;
import com.sravandatha.InventoryService.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/{productId}")
    public ProductInventoryDTO getProductsFromInventory(@PathVariable Integer productId){
        return inventoryService.getProductsFromInventory(productId);
    }

    @PostMapping("/update")
    public InventoryResponse updateProductInventory(@RequestBody UpdateProductInventoryRequest updateProductInventoryRequest){
        return inventoryService.updateProductQuantity(updateProductInventoryRequest);
    }

}
