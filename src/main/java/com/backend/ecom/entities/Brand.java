package com.backend.ecom.entities;

import com.backend.ecom.dto.brand.BrandRequestDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private String name;

    public Brand(BrandRequestDTO brandRequestDTO) {
        this.name = brandRequestDTO.getName();
    }

}

