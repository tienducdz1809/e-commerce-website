package com.backend.ecom.entities;

import com.backend.ecom.dto.shipment.ShipmentRequestDTO;
import com.backend.ecom.supporters.ShipmentStatus;
import lombok.*;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Entity
@Setter
@ToString
@Getter
@NoArgsConstructor
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDate startDate;

    private LocalDate estimatedArrivalDate;

    private ShipmentStatus status;

    //@OneToMany(mappedBy = "shipment")
    //private Set<Transaction> transaction = new HashSet<>();

    public Shipment(ShipmentRequestDTO shipmentRequestDTO){
        this.startDate = shipmentRequestDTO.getStartDate();
        this.estimatedArrivalDate = shipmentRequestDTO.getEstimatedArrivalDate();
        this.status = shipmentRequestDTO.getStatus();
        //this.transaction = shipmentRequestDTO.getTransactions();
    }
}
