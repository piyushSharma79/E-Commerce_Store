package com.backend.ecommerce_store.services;

import com.backend.ecommerce_store.exceptions.*;
import com.backend.ecommerce_store.models.*;
import com.backend.ecommerce_store.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.time.LocalDateTime;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    public Order placeOrder(Integer userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException("user not found"));
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() ->
                        new CartNotFoundException("cart not found "));
        if(cart.getItems().isEmpty()){
            throw new EmptyCartException("Cart is empty");
        }else{
            for(CartItem item : cart.getItems()){
                if(item.getQuantity() > item.getProduct().getStock()){
                    throw new InsufficientStockException("quantity is out of stock");
                }
            }

            Order order =  new Order();
            order.setUser(user);
            order.setStatus(OrderStatus.PLACED);
            order.setOrderDate(LocalDateTime.now());

            double totalAmount = 0;
            order = orderRepository.save(order);

            List<OrderItem> orderItems = new ArrayList<>();
            for(CartItem cartItem : cart.getItems()){

                OrderItem orderItem = new OrderItem();

                orderItem.setOrder(order);

                orderItem.setProduct(
                        cartItem.getProduct()
                );

                orderItem.setQuantity(
                        cartItem.getQuantity()
                );

                orderItem.setPriceAtPurchase(
                        cartItem.getProduct().getPrice()
                );
                double itemTotal =
                        cartItem.getQuantity()
                                * cartItem.getProduct().getPrice();

                totalAmount += itemTotal;

                Product product = cartItem.getProduct();

                product.setStock(
                        product.getStock()
                                - cartItem.getQuantity()
                );
                orderItems.add(orderItem);
                productRepository.save(product);
                orderItemRepository.save(orderItem);
            }
            order.setTotalAmount(totalAmount);
                orderRepository.save(order);
                cartItemRepository.deleteAll(
                        cart.getItems()
                );
            return order;
        }
    }

    public Order getOrderById(Integer orderId){
        return orderRepository.findById(orderId)
                .orElseThrow(() ->
                    new OrderNotFoundException("Order of this id not found")
                );
    }
    public List<Order> getOrdersByUser(Integer userId) {
        return orderRepository.findByUserId(userId);
    }

}
