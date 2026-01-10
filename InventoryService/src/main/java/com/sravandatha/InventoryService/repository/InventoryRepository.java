package com.sravandatha.InventoryService.repository;

import com.sravandatha.InventoryService.entity.ProductInventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<ProductInventoryEntity, Integer> {

    @Query(value = "SELECT p.batch_id, p.product_id, p.product_name, p.quantity, p.expiry_date" +
            " FROM product_inventory p WHERE product_id = :productId ORDER BY expiry_date", nativeQuery = true)
    List<ProductInventoryEntity> findProductsByIdAndOrderByExpiryDate(Integer productId);

}
