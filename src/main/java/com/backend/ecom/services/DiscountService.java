package com.backend.ecom.services;

import com.backend.ecom.dto.discount.DiscountDTO;
import com.backend.ecom.dto.discount.DiscountRequestDTO;
import com.backend.ecom.entities.Discount;
import com.backend.ecom.entities.Product;
import com.backend.ecom.exception.ResourceNotFoundException;
import com.backend.ecom.payload.response.ResponseObject;
import com.backend.ecom.repositories.DiscountRepository;
import com.backend.ecom.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class DiscountService {
    @Autowired
    private DiscountRepository discountRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<Discount> getAllDiscounts() {
        return discountRepository.findAll();
    }

    public ResponseEntity<ResponseObject> getDiscountDetail(Long id) {
        Discount discount = discountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found discount with id: " + id));
        DiscountDTO discountDTO = new DiscountDTO(discount);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Query discount successfully", discountDTO));
    }

    @Transactional
    public ResponseEntity<ResponseObject> createDiscount(DiscountRequestDTO discountRequest) {
        boolean exist = discountRepository.existsByPercentage(discountRequest.getPercentage());
        if (exist) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseObject("error", "Discount is already existed", ""));
        }
        if ((discountRequest.getEndDate()).isBefore(LocalDate.now())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseObject("error", "Date is invalid", discountRequest.getEndDate()));
        }
        Discount discount = new Discount(discountRequest);
        discountRepository.save(discount);
        for (Long productId : discountRequest.getProductIds()) {
            Product product = productRepository.findByIdAndDeleted(productId, false)
                    .orElseThrow(() -> new ResourceNotFoundException("Not found product with id: " + productId));
            product.addDiscount(discount);
            productRepository.save(product);
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Create discount successfully", discount));

    }

    @Transactional
    public ResponseEntity<ResponseObject> updateDiscount(Long id, DiscountRequestDTO discountRequest) {
        Discount discount = discountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found discount with id:" + id));
        if (discount.getPercentage().intValue() != discountRequest.getPercentage().intValue() && discountRepository.existsByPercentage(discountRequest.getPercentage())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseObject("error", "Discount percentage is already existed", discountRequest.getPercentage()));
        }
        if ((discountRequest.getEndDate()).isBefore(LocalDate.now())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseObject("error", "Date is invalid", discountRequest.getEndDate()));
        }
        discount.setPercentage(discountRequest.getPercentage());
        discount.setStartDate(discountRequest.getStartDate());
        discount.setEndDate(discountRequest.getEndDate());
        discountRepository.save(discount);
        List<Product> products = productRepository.findProductsByDiscountIdAndDeleted(id, false);
        products.forEach(product -> product.setDiscount(null));
        for (Long productId : discountRequest.getProductIds()) {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Not found the product with id: " + productId));
            product.addDiscount(discount);
            productRepository.save(product);
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Update discount successfully", discount));
    }

    @Transactional
    public ResponseEntity<ResponseObject> deleteDiscount(Long id) {
        if (!discountRepository.existsById(id)) {
            throw new ResourceNotFoundException("Not found discount with id:" + id);
        }
        List<Product> products = productRepository.findProductsByDiscountIdAndDeleted(id, false);
        products.forEach(product -> {
            product.removeDiscount();
            productRepository.save(product);
        });
        discountRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Delete discount successfully", ""));
    }

}
