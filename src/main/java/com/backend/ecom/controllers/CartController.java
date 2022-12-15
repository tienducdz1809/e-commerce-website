package com.backend.ecom.controllers;

import com.backend.ecom.dto.cart.CartDTO;
import com.backend.ecom.payload.response.ResponseObject;
import com.backend.ecom.services.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/v1/cart")
public class CartController {
    private static final Logger LOG = LoggerFactory.getLogger(CartController.class);
    @Autowired
    private CartService cartService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<CartDTO>> getAllCart() {
        return cartService.getAllCart();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<ResponseObject> getCartDetail(@Valid @NotNull @PathVariable("id") Long id) {
        return cartService.getCartDetail(id);
    }

    @PostMapping("/addToCart/{productId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ResponseObject> addToCart(@Valid @NotNull @PathVariable("productId") Long productId) {
        return cartService.addToCart(productId);
    }

    @DeleteMapping("/delete/{cartItemId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ResponseObject> deleteFromCart(@Valid @NotNull @PathVariable("cartItemId") Long cartItemId) {
        return cartService.deleteFromCart(cartItemId);
    }
}
