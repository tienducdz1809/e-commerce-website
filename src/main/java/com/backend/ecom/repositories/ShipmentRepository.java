package com.backend.ecom.repositories;

import com.backend.ecom.entities.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {

}
