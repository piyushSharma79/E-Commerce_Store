package com.backend.ecommerce_store.repositories;

import com.backend.ecommerce_store.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
//    public Order placeOrder(Integer userId);
}
