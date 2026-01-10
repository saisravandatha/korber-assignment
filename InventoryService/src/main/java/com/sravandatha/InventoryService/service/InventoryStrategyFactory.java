package com.sravandatha.InventoryService.service;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class InventoryStrategyFactory {

    private final Map<InventoryStrategyType, InventoryAllocationStrategy> strategies;

    public InventoryStrategyFactory(
            FifoInventoryStrategy fifoStrategy) {
        strategies = Map.of(
                InventoryStrategyType.FIFO, fifoStrategy);
    }

    public InventoryAllocationStrategy getStrategy(InventoryStrategyType type) {
        InventoryAllocationStrategy strategy = strategies.get(type);
        if (strategy == null) {
            throw new IllegalArgumentException("Unsupported inventory strategy: " + type);
        }
        return strategy;
    }
}
