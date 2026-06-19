package com.backend.ecommerce_store.controllers;

import com.backend.ecommerce_store.models.Cart;
import com.backend.ecommerce_store.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/{userId}/products/{productId}")
    public Cart addProductToCart(@PathVariable Integer userId, @PathVariable Integer productId){
        return cartService.addProductToCart(userId, productId);
    }

    @GetMapping("/{userId}")
    public Cart getCartByUser(@PathVariable Integer userId){
        return cartService.getCartByUser(userId);
    }

    @PutMapping("/{userId}/products/{productId}/remove")
    public Cart removeProductFromCart(@PathVariable Integer userId, @PathVariable Integer productId){
        return cartService.removeProductFromCart(userId, productId);
    }


}
