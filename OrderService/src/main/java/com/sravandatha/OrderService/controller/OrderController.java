package com.sravandatha.OrderService.controller;

import com.sravandatha.OrderService.model.CreateOrderRequest;
import com.sravandatha.OrderService.model.OrderResponse;
import com.sravandatha.OrderService.service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/order")
    public OrderResponse createOrder(@RequestBody CreateOrderRequest createOrderRequest){
        return orderService.createOrder(createOrderRequest);
    }

}
