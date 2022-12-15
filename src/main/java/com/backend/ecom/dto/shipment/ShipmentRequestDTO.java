package com.backend.ecom.dto.shipment;

import com.backend.ecom.entities.Transaction;
import com.backend.ecom.supporters.ShipmentStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;


@Setter
@Getter
@NoArgsConstructor
public class ShipmentRequestDTO {
    @NotNull
   private LocalDate startDate;

    @NotNull
   private LocalDate estimatedArrivalDate;

    @NotNull
   private ShipmentStatus status;

    @NotEmpty
   private Set<Transaction> transactions;
}
