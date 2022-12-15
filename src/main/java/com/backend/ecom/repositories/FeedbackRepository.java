package com.backend.ecom.repositories;

import com.backend.ecom.entities.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    @Query("select f from Feedback f where f.product.id = ?1")
    List<Feedback> findFeedbacksByProductId(Long productId);
}
