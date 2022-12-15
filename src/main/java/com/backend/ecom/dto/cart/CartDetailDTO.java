package com.backend.ecom.dto.cart;

import com.backend.ecom.entities.Cart;
import com.backend.ecom.entities.CartItem;
import com.backend.ecom.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class CartDetailDTO {

    private long id;

    private User user;

    private int countItem;

    private Set<CartItem> cartItems;

    private double totalItemPrice;

    public CartDetailDTO (Cart cart){
        this.id = cart.getId();
        this.user = cart.getUser();
        this.countItem = cart.getCountItem();
        this.cartItems = cart.getCartItems();
        this.totalItemPrice = cart.getTotalItemPrice();
    }
}
