package com.sravandatha.OrderService.client;

import com.sravandatha.OrderService.model.CreateOrderRequest;
import com.sravandatha.OrderService.model.InventoryResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class InventoryClient {

    public InventoryResponse reserveProductInInventory(CreateOrderRequest request){
        RestClient restClient = RestClient.create();
        RestClient.ResponseSpec inventoryResponse = restClient.post()
                .uri("http://localhost:8080/inventory/update")
               .accept(MediaType.APPLICATION_JSON)
               .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
               .body(request)
               .retrieve();
       return inventoryResponse.body(InventoryResponse.class);
    }

}
