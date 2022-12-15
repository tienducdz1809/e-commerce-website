package com.backend.ecom.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Cart {
    @ToString.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;

    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "cart")
    @JsonIgnore
    private User user;

    @Transient
    private int countItem;

    @ToString.Exclude
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private Set<CartItem> cartItems = new java.util.LinkedHashSet<>();

    @Transient
    private double totalItemPrice;

    public void addUser(User user) {
        this.user = user;
        user.setCart(this);
    }

    public void addCartItem(CartItem cartItem) {
        this.cartItems.add(cartItem);
        cartItem.setCart(this);
    }

    public int getCountItem() {
        return cartItems.size();
    }

    public double getTotalItemPrice() {
        totalItemPrice = 0;
        cartItems.forEach(cartItem -> totalItemPrice += cartItem.getTotalPrice());
        return totalItemPrice;
    }

    public void clearCart() {
        this.cartItems.clear();
    }
}
