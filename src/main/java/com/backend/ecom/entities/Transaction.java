package com.backend.ecom.entities;

import com.backend.ecom.supporters.PaymentType;
import com.backend.ecom.supporters.TransactionStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    private PaymentType paymentType;

    private TransactionStatus status;

    private String message;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn
    private Voucher voucher;

    @Transient
    private Double totalPrice;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Cart cart;

    private String shipment;

    public Transaction(User user, PaymentType paymentType, TransactionStatus status, String message,Voucher vouchers, Double totalPrice, Cart cart, Shipment shipment) {
        this.user = user;
        this.paymentType = paymentType;
        this.status = status;
        this.message = message;
        this.voucher = vouchers;
        this.totalPrice = totalPrice;
        this.cart = cart;
        this.shipment = "";
    }

    public Double getTotalPrice(){
        return totalPrice = cart.getTotalItemPrice();
    }
}
