package com.sravandatha.OrderService.service;

import com.sravandatha.OrderService.client.InventoryClient;
import com.sravandatha.OrderService.entity.Order;
import com.sravandatha.OrderService.model.CreateOrderRequest;
import com.sravandatha.OrderService.model.InventoryResponse;
import com.sravandatha.OrderService.model.OrderResponse;
import com.sravandatha.OrderService.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;

    public OrderService(OrderRepository orderRepository, InventoryClient inventoryClient) {
        this.orderRepository = orderRepository;
        this.inventoryClient = inventoryClient;
    }

    public OrderResponse createOrder(CreateOrderRequest createOrderRequest){
        InventoryResponse inventoryResponse = inventoryClient.reserveProductInInventory(createOrderRequest);

        Order order = new Order();
        order.setProductId(createOrderRequest.productId());
        order.setProductName(inventoryResponse.productName());
        order.setQuantity(createOrderRequest.quantity());
        order.setStatus(inventoryResponse.message());
        order.setOrderDate(LocalDate.now());

        Order savedOrder = orderRepository.save(order);

        return OrderResponse.builder()
                .orderId(savedOrder.getOrderId())
                .productId(createOrderRequest.productId())
                .productName(inventoryResponse.productName())
                .status(inventoryResponse.message())
                .reservedFromBatchIds(inventoryResponse.batchIds())
                .quantity(createOrderRequest.quantity())
                .message("Order placed. Inventory reserved.")
                .build();

    }
}