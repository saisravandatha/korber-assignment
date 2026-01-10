package com.sravandatha.InventoryService.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Table(name = "product_inventory")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductInventoryEntity {

    @Id
    @Column(name = "batch_id")
    private Integer batchId;
    @Column(name = "product_id")
    private Integer productId;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "expiry_date")
    private LocalDate expiryDate;

}
