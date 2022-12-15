package com.backend.ecom.dto.product;

import com.backend.ecom.entities.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class ProductDetailDTO {
    private Long id;
    private String thumbnail;
    private String name;
    private String description;
    private ProductDetail productDetail;
    private Integer quantity;
    private Double price;
    private Double totalPrice;
    private String brand;
    private Discount discount;
    private Set<String> categories = new HashSet<>();
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public ProductDetailDTO (Product product){
        this.id = product.getId();
        this.thumbnail = product.getThumbnail();
        this.name = product.getName();
        this.description = product.getDescription();
        this.productDetail = product.getProductDetail();
        this.quantity = product.getQuantity();
        this.price = product.getPrice();
        this.totalPrice = product.getTotalPrice();
        this.brand = product.getBrand().getName();
        product.getCategories().forEach(category -> categories.add(category.getName()));
        this.discount = product.getDiscount();
        this.createdDate = product.getCreatedAt();
        this.updatedDate = product.getUpdatedAt();
    }
}
