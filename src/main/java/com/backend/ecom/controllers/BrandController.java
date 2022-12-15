package com.backend.ecom.controllers;

import com.backend.ecom.dto.brand.BrandDTO;
import com.backend.ecom.dto.brand.BrandRequestDTO;
import com.backend.ecom.payload.response.ResponseObject;
import com.backend.ecom.services.BrandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/brands")
public class BrandController {
    private static final Logger LOG = LoggerFactory.getLogger(BrandController.class);
    @Autowired
    private BrandService brandService;

    @GetMapping("/all")
    public List<BrandDTO> getAllBrands() {
        return brandService.getAllBrands();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getBrandDetail(@Valid @PathVariable("id") Integer id) {
        return brandService.getBrandDetail(id);
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<ResponseObject> getAllProductsByBrandId(@Valid @PathVariable("id") Integer id) {
        return brandService.getAllProductsByBrandId(id);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseObject> createBrand(@Valid @RequestBody BrandRequestDTO brandRequest) {
        return brandService.createBrand(brandRequest);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseObject> updateBrand(@Valid @PathVariable("id") Integer id,
                                                      @Valid @RequestBody BrandRequestDTO brandRequest) {
        return brandService.updateBrand(id, brandRequest);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseObject> deleteBrand(@Valid @PathVariable("id") Integer id) {
        return brandService.deleteBrand(id);
    }
}