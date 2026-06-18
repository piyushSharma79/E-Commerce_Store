package com.backend.ecommerce_store.repositories;

import com.backend.ecommerce_store.models.Cart;
import com.backend.ecommerce_store.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    Optional<Cart> findByUser(User user);
}
