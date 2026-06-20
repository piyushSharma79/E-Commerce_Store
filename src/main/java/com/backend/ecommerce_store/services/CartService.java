package com.backend.ecommerce_store.services;

import com.backend.ecommerce_store.exceptions.CartItemNotFoundException;
import com.backend.ecommerce_store.exceptions.CartNotFoundException;
import com.backend.ecommerce_store.exceptions.ProductNotFoundException;
import com.backend.ecommerce_store.exceptions.UserNotFoundException;
import com.backend.ecommerce_store.models.Cart;
import com.backend.ecommerce_store.models.CartItem;
import com.backend.ecommerce_store.models.Product;
import com.backend.ecommerce_store.models.User;
import com.backend.ecommerce_store.repositories.CartItemRepository;
import com.backend.ecommerce_store.repositories.CartRepository;
import com.backend.ecommerce_store.repositories.ProductRepository;
import com.backend.ecommerce_store.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

    public Cart createCartForUser(Integer userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                    new UserNotFoundException("User not found")
                );
        Optional<Cart> existingCart = cartRepository.findByUser(user);
        if(existingCart.isPresent()){
            return existingCart.get();
        }else{
            Cart cart = new Cart();
            cart.setUser(user);
            return cartRepository.save(cart);
        }
    }

    public Cart addProductToCart(Integer userId, Integer productId ){
        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ProductNotFoundException("Product not found"));
        Cart cart = createCartForUser(userId);
        Optional<CartItem> existingItem =
                cartItemRepository.findByCartAndProduct(cart, product);
        if(existingItem.isPresent()){
            CartItem cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            cartItemRepository.save(cartItem);
        }else{
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(1);
            cartItemRepository.save(cartItem);
        }
        return cart;
    }

    public Cart getCartByUser(Integer userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

        return cartRepository.findByUser(user)
                .orElseThrow(() ->
                        new CartNotFoundException("Cart not found"));
    }

    public Cart removeProductFromCart(Integer userId, Integer productId){
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ProductNotFoundException("product not found"));
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() ->
                        new CartNotFoundException("Cart not found"));

        CartItem cartItem = cartItemRepository
                .findByCartAndProduct(cart, product)
                .orElseThrow(() ->
                        new CartItemNotFoundException("Product not present in cart"));
        if (cartItem.getQuantity() > 1) {

            cartItem.setQuantity(
                    cartItem.getQuantity() - 1
            );

            cartItemRepository.save(cartItem);

        } else {

            cartItemRepository.delete(cartItem);

        }

        return cart;
    }
}
