package com.backend.ecom.services;

import com.backend.ecom.entities.CartItem;
import com.backend.ecom.entities.Product;
import com.backend.ecom.exception.ResourceNotFoundException;
import com.backend.ecom.repositories.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartItemService {
    @Autowired
    private CartItemRepository cartItemRepository;

    public CartItem createCartItem (Product product) {
        CartItem cartItem = new CartItem();
        product.addCartItem(cartItem);
        return cartItem;
    }

    public CartItem saveCartItem(CartItem cartItem){
        return cartItemRepository.save(cartItem);
    }

    public Optional<CartItem>  getCartItemByCartId (Long cartId, Long productId){
        Optional<CartItem> cartItem = cartItemRepository.findCartItemByCartId(cartId, productId);
        return cartItem;
    }

    public void deleteCartItem (Long id) {
        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Not found cart item with id: " + id));
        cartItemRepository.delete(cartItem);
    }
}
