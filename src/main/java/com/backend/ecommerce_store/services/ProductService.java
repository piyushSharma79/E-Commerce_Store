package com.backend.ecommerce_store.services;

import com.backend.ecommerce_store.models.Product;
import com.backend.ecommerce_store.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product addProduct(Product product){
        return productRepository.save(product);
    }
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }
    public Optional<Product> getProductById(Integer id){
        return productRepository.findById(id);
    }

    public void deleteProduct(Integer id){
        productRepository.deleteById(id);
    }
}
