package com.backend.ecom.services;

import com.backend.ecom.dto.shipment.ShipmentDTO;
import com.backend.ecom.dto.shipment.ShipmentRequestDTO;
import com.backend.ecom.entities.Shipment;
import com.backend.ecom.exception.ResourceNotFoundException;
import com.backend.ecom.payload.response.ResponseObject;
import com.backend.ecom.repositories.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShipmentService {
    @Autowired
    ShipmentRepository shipmentRepository;

    public List<Shipment> getAllShipment(){
        return shipmentRepository.findAll();
    }

    public ResponseEntity<ResponseObject> getShipmentDetail(Long id){
        Shipment shipment = shipmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find shipment with id: " +id));
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Shipment found!", new ShipmentDTO(shipment)));
    }

    public ResponseEntity<ResponseObject> updateShipment(Long id, ShipmentRequestDTO updateShipment){
        Shipment shipment = shipmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find shipment with id: " +id));

        shipment.setStatus(updateShipment.getStatus());
        shipment.setEstimatedArrivalDate(updateShipment.getEstimatedArrivalDate());
        shipment.setStartDate(updateShipment.getStartDate());
        //shipment.setTransaction(updateShipment.getTransactions());

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Shipment info updated!", new ShipmentDTO(shipmentRepository.save(shipment))));
    }

    public ResponseEntity<ResponseObject> deleteShipment(Long id){
        Shipment shipment = shipmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find shipment with id: " +id));
        shipmentRepository.delete(shipment);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Shipment deleted!", ""));
    }

    public ResponseEntity<ResponseObject> createShipment(ShipmentRequestDTO shipmentRequestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject("ok", "Shipment created!", new ShipmentDTO(shipmentRepository.save(new Shipment(shipmentRequestDTO)))));
    }

}
