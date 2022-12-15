package com.backend.ecom.entities;

import com.backend.ecom.dto.category.CategoryRequestDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @ToString.Exclude
    @ManyToMany(mappedBy = "categories", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnore
    private Set<Product> products = new java.util.LinkedHashSet<>();

    public Category(CategoryRequestDTO categoryRequestDTO){
        this.name = categoryRequestDTO.getName();
    }
}
