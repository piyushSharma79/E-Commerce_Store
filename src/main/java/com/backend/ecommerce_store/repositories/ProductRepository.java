package com.backend.ecommerce_store.repositories;

import com.backend.ecommerce_store.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {


}
