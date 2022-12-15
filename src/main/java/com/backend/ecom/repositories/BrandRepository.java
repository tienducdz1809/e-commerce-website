package com.backend.ecom.repositories;

import com.backend.ecom.entities.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {

    Boolean existsByName(String name);
}
