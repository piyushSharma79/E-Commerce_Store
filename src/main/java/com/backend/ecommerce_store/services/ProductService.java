package com.backend.ecommerce_store.services;

import com.backend.ecommerce_store.exceptions.ProductNotFoundException;
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
    public Product getProductById(Integer id){
        return productRepository.findById(id)
            .orElseThrow(() ->
                        new ProductNotFoundException("Product not found"));
    }
    public void deleteProduct(Integer id){
        productRepository.deleteById(id);
    }
    public Product updateProduct(Integer id, Product product){
        Product existingProduct =
                productRepository.findById(id)
                        .orElseThrow (() -> new ProductNotFoundException("Product with this id is not found"));
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice( product.getPrice());
        existingProduct.setImageUrl( product.getImageUrl());
        existingProduct.setStock( product.getStock());
        return this.productRepository.save(existingProduct);
    }
}
