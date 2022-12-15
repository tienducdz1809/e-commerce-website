package com.backend.ecom.repositories;

import com.backend.ecom.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Boolean existsByName(String name);

    @Query("select (count(p) > 0) from Product p where p.id = ?1 and p.deleted = ?2")
    Boolean existsByIdAndDeleted(Long id, Boolean deleted);

    @Query("select p from Product p where p.id = ?1 and p.deleted = ?2")
    Optional<Product> findByIdAndDeleted(Long id, Boolean deleted);

    List<Product> findAllByDeleted(Boolean deleted);

    @Query("select p from Product p inner join p.categories categories where categories.id = ?1 and p.deleted = ?2")
    List<Product> findProductsByCategories_idAndDeleted(Long categoryId, Boolean deleted);

    @Query("select p from Product p where p.brand.id = ?1 and p.deleted = ?2")
    List<Product> findProductsByBrandIdAndDeleted(Integer brandId, Boolean deleted);

    @Query("select p from Product p where p.discount.id = ?1 and p.deleted = ?2")
    List<Product> findProductsByDiscountIdAndDeleted(Long discountId, Boolean deleted);

    @Modifying
    @Query("UPDATE Product SET deleted = true, deletedAt = current_date WHERE id in ?1")
    void softDeleteAllByIds(Iterable<? extends Long> ids);

    @Modifying
    @Query("UPDATE Product SET deleted = false WHERE id in ?1")
    void restoreAllByIds(Iterable<? extends Long> ids);

    void deleteAllByBrandId(Integer brandId);

    @Query("select p from Product p left join fetch p.categories categories where " +
            "p.deleted = ?3 and" +
            "(p.name like concat('%', ?1, '%')" +
            "or p.brand.name like concat('%', ?1, '%')" +
            "or p.description like concat('%', ?1, '%'))" +
            "and categories.name like concat('%', ?2, '%')")
    List<Product> searchProduct(String query, String categories, Boolean deleted);

}
