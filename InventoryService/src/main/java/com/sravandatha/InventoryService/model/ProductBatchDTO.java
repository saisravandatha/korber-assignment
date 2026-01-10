package com.sravandatha.InventoryService.model;

import lombok.Builder;

import java.time.LocalDate;
import java.util.Date;

@Builder
public record ProductBatchDTO(Integer batchId, Integer quantity,
                              LocalDate expiryDate) {
}
