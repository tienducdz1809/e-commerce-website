package com.backend.ecom.services;

import com.backend.ecom.dto.brand.BrandDTO;
import com.backend.ecom.dto.brand.BrandRequestDTO;
import com.backend.ecom.dto.product.ProductShortInfoDTO;
import com.backend.ecom.entities.Brand;
import com.backend.ecom.entities.Product;
import com.backend.ecom.exception.ResourceNotFoundException;
import com.backend.ecom.payload.response.ResponseObject;
import com.backend.ecom.repositories.BrandRepository;
import com.backend.ecom.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class BrandService {
    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<BrandDTO> getAllBrands() {
        List<Brand> brands = brandRepository.findAll();
        List<BrandDTO> brandDTOS = new ArrayList<>();
        brands.forEach(brand -> brandDTOS.add(new BrandDTO(brand)));
        return brandDTOS;
    }

    public ResponseEntity<ResponseObject> getBrandDetail(Integer id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found brand with id: " + id));
        BrandDTO brandDTO = new BrandDTO(brand);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Query brand successfully", brandDTO));
    }

    public ResponseEntity<ResponseObject> getAllProductsByBrandId(Integer id) {
        if (!brandRepository.existsById(id)) {
            throw new ResourceNotFoundException("Not found brand with id:" + id);
        }
        List<ProductShortInfoDTO> productsShortInfo = new ArrayList<>();
        List<Product> products = productRepository.findProductsByBrandIdAndDeleted(id, false);

        products.forEach(product -> productsShortInfo.add(new ProductShortInfoDTO(product)));

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Query products successfully", productsShortInfo));

    }

    public ResponseEntity<ResponseObject> createBrand(BrandRequestDTO brandRequest) {
        boolean exist = brandRepository.existsByName(brandRequest.getName().trim());
        if (exist) {
            throw new ResourceNotFoundException("Brand is already existed");
        }
        Brand brand = new Brand(brandRequest);
        brandRepository.save(brand);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Create brand successfully", brand));
    }

    public ResponseEntity<ResponseObject> updateBrand(Integer id, BrandRequestDTO brandRequest) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found brand with id:" + id));
        boolean existName = brandRepository.existsByName(brandRequest.getName().trim());
        if (!brand.getName().equals(brandRequest.getName().trim()) && existName) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseObject("error", "Brand name is already existed", ""));
        }
        brand.setName(brandRequest.getName());
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Update brand successfully", brandRepository.save(brand)));
    }

    @Transactional
    public ResponseEntity<ResponseObject> deleteBrand(Integer id) {
        if (!brandRepository.existsById(id)) {
            throw new ResourceNotFoundException("Not found brand with id:" + id);
        }
        productRepository.deleteAllByBrandId(id);
        brandRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Delete brand successfully", ""));
    }

}
