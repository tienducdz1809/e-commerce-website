package com.backend.ecom.entities;

import com.backend.ecom.dto.discount.DiscountRequestDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer percentage;

    private LocalDate startDate;

    private LocalDate endDate;

    @ToString.Exclude
    @OneToMany(mappedBy = "discount")
    @JsonIgnore
    private Set<Product> products = new java.util.LinkedHashSet<>();
   public Discount(DiscountRequestDTO discountRequestDTO){
       this.percentage = discountRequestDTO.getPercentage();
       this.startDate = discountRequestDTO.getStartDate();
       this.endDate = discountRequestDTO.getEndDate();
   }
}
