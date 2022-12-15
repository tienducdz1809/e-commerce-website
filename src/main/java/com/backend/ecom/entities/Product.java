package com.backend.ecom.entities;

import com.backend.ecom.dto.product.ProductRequestDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String thumbnail;

    private String name;

    @Lob
    private String description;

    private int quantity;

    private Double price;

    @Transient
    private Double totalPrice;

    @ToString.Exclude
    @OneToMany(mappedBy = "product")
    private Set<ProductImage> images = new java.util.LinkedHashSet<>();

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "brandId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Brand brand;

    @Embedded
    private ProductDetail productDetail;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Discount discount;

    @ToString.Exclude
    @OneToMany(mappedBy = "product")
    private Set<Feedback> feedbacks = new java.util.LinkedHashSet<>();

    @ToString.Exclude
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "product_categories",
            joinColumns = @JoinColumn(name = "productId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "categoryId", referencedColumnName = "id"))
    private Set<Category> categories = new java.util.LinkedHashSet<>();
    @ToString.Exclude
    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private Set<CartItem> cartItems = new java.util.LinkedHashSet<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @JsonIgnore
    private boolean deleted = Boolean.FALSE;

    private LocalDateTime deletedAt;

    public Product(ProductRequestDTO productRequestDTO) {
        this.name = productRequestDTO.getName();
        this.description = productRequestDTO.getDescription();
        this.quantity = productRequestDTO.getQuantity();
        this.price = productRequestDTO.getPrice();
    }

    public Double getTotalPrice() {
        if (isValidDiscount()) {
            return price - (price * getDiscount().getPercentage() / 100);
        }
        return price;
    }

    private boolean isValidDiscount() {
        if (getDiscount() == null) {
            return false;
        }
        LocalDate date = LocalDate.now();
        if (date.isAfter(getDiscount().getStartDate()) && date.isBefore(getDiscount().getEndDate())) {
            return true;
        } else {
            this.setDiscount(null);
            return false;
        }
    }

    public void addCartItem(CartItem cartItem){
        this.cartItems.add(cartItem);
        cartItem.setProduct(this);
    }

    public void addCategory(Category category) {
        this.categories.add(category);
        category.getProducts().add(this);
    }

    public void removeCategory(Long categoryId) {
        Category category = this.categories.stream().filter(t -> t.getId() == categoryId).findFirst().orElse(null);
        if (category != null) this.categories.remove(category);
        category.getProducts().remove(this);
    }

    public void addDiscount(Discount discount) {
        this.discount = discount;
        discount.getProducts().add(this);
    }

    public void removeDiscount() {
        getDiscount().getProducts().remove(this);
        this.discount = null;
    }
}
