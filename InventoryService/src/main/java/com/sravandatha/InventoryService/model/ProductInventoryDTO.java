package com.sravandatha.InventoryService.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
public record ProductInventoryDTO(Integer productId,
                                  String productName, List<ProductBatchDTO> batches) {
}
