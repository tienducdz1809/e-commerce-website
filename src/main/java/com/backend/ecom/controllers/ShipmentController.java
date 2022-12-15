package com.backend.ecom.controllers;

import com.backend.ecom.dto.shipment.ShipmentRequestDTO;
import com.backend.ecom.entities.Shipment;
import com.backend.ecom.payload.response.ResponseObject;
import com.backend.ecom.services.ShipmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shipment")
public class ShipmentController {
    private static final Logger LOG = LoggerFactory.getLogger(ShipmentController.class);
    @Autowired
    private ShipmentService shipmentService;

    @GetMapping("/all")
    public List<Shipment> getAllShipment(){
        return shipmentService.getAllShipment();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getShipmentDetails(@PathVariable Long id){
        return shipmentService.getShipmentDetail(id);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createShipment(@RequestBody ShipmentRequestDTO shipment){
        return shipmentService.createShipment(shipment);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> updateShipment(@PathVariable Long id, @RequestBody ShipmentRequestDTO updateShipment){
        return shipmentService.updateShipment(id, updateShipment);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> deleteMapping(@PathVariable Long id){
        return shipmentService.deleteShipment(id);
    }
}
