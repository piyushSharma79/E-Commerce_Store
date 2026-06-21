package com.backend.ecommerce_store.repositories;

import com.backend.ecommerce_store.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

}
