package com.backend.ecommerce_store.repositories;

import com.backend.ecommerce_store.models.Cart;
import com.backend.ecommerce_store.models.CartItem;
import com.backend.ecommerce_store.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    Optional<CartItem> findByCartAndProduct(
            Cart cart,
            Product product
    );
}
