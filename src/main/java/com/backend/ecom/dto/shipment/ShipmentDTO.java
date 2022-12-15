package com.backend.ecom.dto.shipment;

import com.backend.ecom.entities.Shipment;
import com.backend.ecom.entities.Transaction;
import com.backend.ecom.supporters.ShipmentStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
public class ShipmentDTO {
    private long id;

    private LocalDate startDate;

    private LocalDate estimatedArrivalDate;

    private ShipmentStatus status;

    private Set<Transaction> transactions;

    public ShipmentDTO(Shipment shipment){
        this.id = shipment.getId();
        this.startDate = shipment.getStartDate();
        this.estimatedArrivalDate = shipment.getEstimatedArrivalDate();
        this.status = shipment.getStatus();
        //this.transactions = shipment.getTransaction();
    }
}
