package com.mongo.inventoryservice.repo;

import com.mongo.inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepo extends JpaRepository<Inventory,Long> {


    List<Inventory> findBySkuCodeIn(List<String> skuCode);
}
