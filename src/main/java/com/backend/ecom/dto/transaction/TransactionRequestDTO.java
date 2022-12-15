package com.backend.ecom.dto.transaction;

import com.backend.ecom.supporters.PaymentType;
import com.backend.ecom.supporters.TransactionStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
public class TransactionRequestDTO {
    @NotNull
    private PaymentType paymentType;

    @NotNull
    private TransactionStatus status;

    @Nullable
    private long voucherID;

    @NotNull
    private Double totalPrice;

    @NotNull
    private String message;


}
