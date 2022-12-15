package com.backend.ecom.repositories;

import com.backend.ecom.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @Query("select c from CartItem c where c.cart.id = ?1 and c.product.id = ?2")
    Optional<CartItem> findCartItemByCartId(Long cartId, Long productId);
}
