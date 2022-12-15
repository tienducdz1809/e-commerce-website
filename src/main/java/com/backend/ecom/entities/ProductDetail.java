package com.backend.ecom.entities;

import com.backend.ecom.dto.product.ProductRequestDTO;
import lombok.*;

import javax.persistence.Embeddable;

@AllArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
@Embeddable
public class ProductDetail {
    private String screen;

    private String os;

    private String camera;

    private String processor;

    private Integer ram;

    private Integer internalStorage;

    private Integer battery;

    public ProductDetail(ProductRequestDTO productRequestDTO){
       this.screen = productRequestDTO.getScreen();
       this.os = productRequestDTO.getOs();
       this.camera = productRequestDTO.getCamera();
       this.processor = productRequestDTO.getProcessor();
       this.ram = productRequestDTO.getRam();
       this.internalStorage = productRequestDTO.getInternalStorage();
       this.battery = productRequestDTO.getBattery();
    }
}
