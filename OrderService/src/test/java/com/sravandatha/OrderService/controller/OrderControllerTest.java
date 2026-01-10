package com.sravandatha.OrderService.controller;

import com.sravandatha.OrderService.model.CreateOrderRequest;
import com.sravandatha.OrderService.model.OrderResponse;
import com.sravandatha.OrderService.service.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createOrder_shouldReturnOrderResponse() throws Exception {
        // Arrange
        CreateOrderRequest request = new CreateOrderRequest(1, 10);

        OrderResponse response = new OrderResponse(1,1,"phone",10,"PLACED", List.of(1),"Order placed. Inventory reserved.");

        Mockito.when(orderService.createOrder(Mockito.any(CreateOrderRequest.class)))
                .thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(1))
                .andExpect(jsonPath("$.status").value("PLACED"));
    }
}
