package com.ecommerce.order.service;

import com.ecommerce.order.clients.ProductServiceClient;
import com.ecommerce.order.clients.UserServiceClient;
import com.ecommerce.order.dto.CartItemRequest;
import com.ecommerce.order.dto.ProductResponse;
import com.ecommerce.order.dto.UserResponse;
import com.ecommerce.order.exception.CartItemNotFoundException;
import com.ecommerce.order.exception.OutOfStockException;
import com.ecommerce.order.exception.ProductNotFoundException;
import com.ecommerce.order.exception.UserNotFoundException;
import com.ecommerce.order.model.CartItem;
import com.ecommerce.order.repository.CartItemRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;



@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final UserServiceClient userServiceClient;
    private final ProductServiceClient productServiceClient;


    public void addToCart(String userId, CartItemRequest request) {
        //Look for product
        ProductResponse productResponse;
        
        try {
            productResponse = productServiceClient.getProductDetails(request.getProductId());
        } catch (Exception e) {
            throw new ProductNotFoundException("Product not found with ID: " + request.getProductId());
        }
       
        if(productResponse == null) {
             throw new ProductNotFoundException("Product not found with ID: " + request.getProductId());
        }

        if(productResponse.getStockQuantity() < request.getQuantity()) {
            throw new OutOfStockException("Product " + productResponse.getName() + " is out of stock or insufficient quantity.");
        }
        
        UserResponse userResponse;

        try {
            userResponse = userServiceClient.getUserDetails(userId);
        } catch (Exception e) {
            throw new UserNotFoundException("User not found with ID: " + userId);
        }
        
        if(userResponse == null) {
            throw new UserNotFoundException("User not found with ID: " + userId);
        }

        CartItem existingCartItem = cartItemRepository.findByUserIdAndProductId(userId, request.getProductId());
        if(existingCartItem != null) {
            //Update the quantity
            existingCartItem.setQuantity(existingCartItem.getQuantity() + request.getQuantity());
            existingCartItem.setPrice(productResponse.getPrice());
            cartItemRepository.save(existingCartItem);
        } else {
            //Create new cart item
            CartItem cartItem = new CartItem();
            cartItem.setUserId(userId);
            cartItem.setProductId(request.getProductId());
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(productResponse.getPrice());
            cartItemRepository.save(cartItem);
       }
    }


    
    public void deleteItemFromCart(String userId, String productId) {
       CartItem cartItem = cartItemRepository.findByUserIdAndProductId(userId, productId);
        if(cartItem != null){
            cartItemRepository.delete(cartItem);
        } else {
            throw new CartItemNotFoundException("Cart item not found for product ID: " + productId);
        }
    }

    
    public void clearCart(String userId) {
        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);
        cartItemRepository.deleteAll(cartItems);
    }

    public List<CartItem> getCart(String userId) {
       return cartItemRepository.findByUserId(userId);
    }
}
