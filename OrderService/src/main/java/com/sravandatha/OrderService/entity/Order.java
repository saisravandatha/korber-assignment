package com.sravandatha.OrderService.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Table(name = "orders_table")
@Entity
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_sequence")
    @SequenceGenerator(name = "order_sequence", sequenceName = "order_sequence", allocationSize = 1)
    @Column(name = "order_id")
    private Integer orderId;
    @Column(name = "product_id")
    private Integer productId;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "status")
    private String status;
    @Column(name = "order_date")
    private LocalDate orderDate;

}