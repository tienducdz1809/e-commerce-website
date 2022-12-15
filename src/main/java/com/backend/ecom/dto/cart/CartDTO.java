package com.backend.ecom.dto.cart;

import com.backend.ecom.entities.Cart;
import com.backend.ecom.entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartDTO {
    private long id;

    private User user;

    private int countItem;

    private double totalItemPrice;

    public CartDTO(Cart cart) {
        this.id = cart.getId();
        this.user = cart.getUser();
        this.countItem = cart.getCountItem();
        this.totalItemPrice = cart.getTotalItemPrice();
    }
}
