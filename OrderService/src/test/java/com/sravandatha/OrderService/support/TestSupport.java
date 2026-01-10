package com.sravandatha.OrderService.support;

import com.sravandatha.OrderService.entity.Order;
import com.sravandatha.OrderService.model.CreateOrderRequest;
import com.sravandatha.OrderService.model.InventoryResponse;
import com.sravandatha.OrderService.model.OrderResponse;

import java.time.LocalDate;
import java.util.List;

public class TestSupport {

    public static CreateOrderRequest orderRequest(){
        return CreateOrderRequest.builder()
                .productId(1001)
                .quantity(10)
                .build();
    }

    public static InventoryResponse inventoryResponse(){
        return InventoryResponse.builder()
                .message("PLACED")
                .productName("SmartPhone")
                .batchIds(List.of(3))
                .build();
    }

    public static OrderResponse orderResponse(){
        return OrderResponse.builder()
                .orderId(4145)
                .status("PLACED")
                .reservedFromBatchIds(List.of(3))
                .productName("SmartPhone")
                .productId(1001)
                .quantity(10)
                .message("Order placed. Inventory reserved.")
                .build();
    }

    public static Order order() {
        Order order = new Order();
        order.setOrderId(1);
        order.setOrderDate(LocalDate.now());
        order.setStatus("PLACED");
        order.setQuantity(10);
        order.setProductId(10);
        return order;
    }
}
