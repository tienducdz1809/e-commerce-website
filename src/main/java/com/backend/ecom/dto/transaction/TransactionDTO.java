package com.backend.ecom.dto.transaction;

import com.backend.ecom.entities.Transaction;
import com.backend.ecom.entities.Voucher;
import com.backend.ecom.supporters.PaymentType;
import com.backend.ecom.supporters.TransactionStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class TransactionDTO {
    private long id;

    private long user;

    private PaymentType paymentType;

    private String message;

    private Voucher voucher;

    private Double totalPrice;

    private LocalDateTime createAt;

    private long cart;

    private long shipment;

    private TransactionStatus status;

    public TransactionDTO(Transaction transaction){
        this.id = transaction.getId();
        this.user = transaction.getUser().getId();
        this.paymentType = transaction.getPaymentType();
        this.message = transaction.getMessage();
        this.voucher = transaction.getVoucher();
        this.totalPrice = transaction.getTotalPrice();
        this.createAt = transaction.getCreatedAt();
        this.cart = transaction.getCart().getId();
        this.shipment = 1;
        this.status = transaction.getStatus();
    }
}
