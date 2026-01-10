package com.sravandatha.OrderService.repository;

import com.sravandatha.OrderService.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {



}
