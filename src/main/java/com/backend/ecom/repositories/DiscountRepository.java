package com.backend.ecom.repositories;

import com.backend.ecom.entities.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {
    boolean existsByPercentage(Integer percentage);
}
