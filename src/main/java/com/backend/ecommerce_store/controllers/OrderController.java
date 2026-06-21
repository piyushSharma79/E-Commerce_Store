package com.backend.ecommerce_store.controllers;

import com.backend.ecommerce_store.models.Order;
import com.backend.ecommerce_store.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/{userId}/place")
    public Order placeOrder(@PathVariable Integer userId){
        return orderService.placeOrder(userId);
    }

    @GetMapping("/{orderId}")
    public Order getOrderById(@PathVariable Integer orderId){
        return orderService.getOrderById(orderId);
    }
}
