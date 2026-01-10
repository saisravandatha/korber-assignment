package com.sravandatha.OrderService.service;

import com.sravandatha.OrderService.client.InventoryClient;
import com.sravandatha.OrderService.entity.Order;
import com.sravandatha.OrderService.model.CreateOrderRequest;
import com.sravandatha.OrderService.model.InventoryResponse;
import com.sravandatha.OrderService.model.OrderResponse;
import com.sravandatha.OrderService.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private InventoryClient inventoryClient;

    @InjectMocks
    private OrderService orderService;

    @Test
    void createOrder_shouldCreateAndSaveOrderSuccessfully() {
        // Given
        CreateOrderRequest request = new CreateOrderRequest(1, 2);

        InventoryResponse inventoryResponse = InventoryResponse.builder()
                .productName("Phone")
                .message("PLACED")
                .batchIds(List.of(1))
                .build();

        when(inventoryClient.reserveProductInInventory(request))
                .thenReturn(inventoryResponse);

        Order savedOrder = new Order();
        savedOrder.setOrderId(1);

        when(orderRepository.save(any(Order.class)))
                .thenReturn(savedOrder);

        // When
        OrderResponse response = orderService.createOrder(request);

        // Then
        assertNotNull(response);
        assertEquals(1, response.orderId());
        assertEquals(1, response.productId());
        assertEquals("Phone", response.productName());
        assertEquals("PLACED", response.status());
        assertEquals(2, response.quantity());
        assertEquals(List.of(1), response.reservedFromBatchIds());
        assertEquals("Order placed. Inventory reserved.", response.message());

        // Capture and verify saved Order entity
        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderRepository).save(orderCaptor.capture());

        Order capturedOrder = orderCaptor.getValue();
        assertEquals(1, capturedOrder.getProductId());
        assertEquals("Phone", capturedOrder.getProductName());
        assertEquals(2, capturedOrder.getQuantity());
        assertEquals("PLACED", capturedOrder.getStatus());
        assertEquals(LocalDate.now(), capturedOrder.getOrderDate());

        // Verify interactions
        verify(inventoryClient, times(1))
                .reserveProductInInventory(request);
        verify(orderRepository, times(1))
                .save(any(Order.class));
    }
}
