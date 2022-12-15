package com.backend.ecom.repositories;

import com.backend.ecom.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Boolean existsByName(String name);

    @Query("select c from Category c inner join c.products products where products.id = ?1")
    List<Category> findByProductId(Long productId);
}
