package com.backend.ecom.dto.brand;

import com.backend.ecom.entities.Brand;
import com.backend.ecom.entities.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class BrandDTO {
    private int id;
    private String name;

    public BrandDTO(Brand brand){
        this.id = brand.getId();
        this.name = brand.getName();
    }
}
