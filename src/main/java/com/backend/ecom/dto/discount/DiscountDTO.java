package com.backend.ecom.dto.discount;

import com.backend.ecom.entities.Discount;
import com.backend.ecom.entities.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class DiscountDTO {
    private long id;

    private Integer percentage;

    private LocalDate startDate;

    private LocalDate endDate;

    private Set<String> products = new HashSet<>();
    public DiscountDTO(Discount discount){
        this.id = discount.getId();
        this.percentage = discount.getPercentage();
        this.startDate = discount.getStartDate();
        this.endDate = discount.getEndDate();
        discount.getProducts().forEach(product -> products.add(product.getName()));

    }
}
