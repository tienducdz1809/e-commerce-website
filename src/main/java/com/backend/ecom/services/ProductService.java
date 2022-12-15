package com.backend.ecom.services;

import com.backend.ecom.dto.feedback.FeedbackDTO;
import com.backend.ecom.dto.feedback.FeedbackRequestDTO;
import com.backend.ecom.dto.product.ProductDetailDTO;
import com.backend.ecom.dto.product.ProductRequestDTO;
import com.backend.ecom.dto.product.ProductShortInfoDTO;
import com.backend.ecom.entities.*;
import com.backend.ecom.exception.ResourceNotFoundException;
import com.backend.ecom.payload.response.ResponseObject;
import com.backend.ecom.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private UserRepository userRepository;

    public List<ProductShortInfoDTO> getAllProducts(Boolean deleted) {
        List<ProductShortInfoDTO> productShortInfo = new ArrayList<>();
        List<Product> products = productRepository.findAllByDeleted(deleted);

        products.forEach(product -> productShortInfo.add(new ProductShortInfoDTO(product)));

        return productShortInfo;
    }

    public ResponseEntity<ResponseObject> getProductDetail(Long id, Boolean deleted) {
        Product product = productRepository.findByIdAndDeleted(id, deleted)
                .orElseThrow(() -> new ResourceNotFoundException("Not found product with id: " + id));
        ProductDetailDTO productDetail = new ProductDetailDTO(product);
        return ResponseEntity.ok(new ResponseObject("ok", "Query product successfully", productDetail));
    }

    public ResponseEntity<ResponseObject> getAllCategoriesByProductId(Long productId) {
        if (!productRepository.existsByIdAndDeleted(productId, false)) {
            throw new ResourceNotFoundException("Not found product with id: " + productId);
        }
        List<Category> categories = categoryRepository.findByProductId(productId);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Query categories successfully", categories));

    }

    public ResponseEntity<ResponseObject> getAllFeedbacksByProductId(Long productId) {
        if (!productRepository.existsByIdAndDeleted(productId, false)) {
            throw new ResourceNotFoundException("Not found product with id: " + productId);
        }
        List<Feedback> feedbacks = feedbackRepository.findFeedbacksByProductId(productId);
        List<FeedbackDTO> feedbackDTOS = new ArrayList<>();
        feedbacks.forEach(feedback -> feedbackDTOS.add(new FeedbackDTO(feedback)));
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Query feedbacks successfully", feedbackDTOS));

    }

    @Transactional
    public ResponseEntity<ResponseObject> createProduct(ProductRequestDTO productRequestDTO) {
        boolean exist = productRepository.existsByName(productRequestDTO.getName().trim());
        if (exist) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseObject("error", "Product is already existed", ""));
        }
        Product product = new Product(productRequestDTO);
        if (!productRequestDTO.getThumbnail().equals(null) && !productRequestDTO.getThumbnail().equals("")) {
            product.setThumbnail(productRequestDTO.getThumbnail().trim());
        }
        for (Long categoryId : productRequestDTO.getCategoryIds()) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new ResourceNotFoundException("Not found category with id: " + categoryId));
            product.addCategory(category);
        }
        Brand brand = brandRepository.findById(productRequestDTO.getBrandId())
                .orElseThrow(() -> new ResourceNotFoundException("Not found brand with id: " + productRequestDTO.getBrandId()));
        product.setBrand(brand);
        ProductDetail productDetail = new ProductDetail(productRequestDTO);
        product.setProductDetail(productDetail);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Create product successfully", productRepository.save(product)));
    }

    public ResponseEntity<ResponseObject> createFeedbackFromProduct(Long id, FeedbackRequestDTO feedbackRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Feedback feedback = new Feedback(feedbackRequest);
        Product product = productRepository.getById(id);
        feedback.setProduct(product);
        User user = userRepository.getByUsername(auth.getName());
        feedback.setUser(user);
        feedbackRepository.save(feedback);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Write feedback successfully", feedback));
    }

    @Transactional
    public ResponseEntity<ResponseObject> updateProduct(Long id, ProductRequestDTO productRequest) {
        Product product = productRepository.findByIdAndDeleted(id, false)
                .orElseThrow(() -> new ResourceNotFoundException("Not found product with id = " + id));
        if (!product.getName().equals(productRequest.getName().trim()) && productRepository.existsByName(productRequest.getName().trim())) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("error", "Product name is already existed", productRequest.getName()));
        }
        product.setName(productRequest.getName().trim());
        product.setDescription(productRequest.getDescription().trim());
        product.setQuantity(productRequest.getQuantity());
        product.setPrice(productRequest.getPrice());
        product.setCategories(new HashSet<>());
        product.setProductDetail(new ProductDetail(productRequest));
        for (Long categoryId : productRequest.getCategoryIds()) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new ResourceNotFoundException("Not found the category with id: " + categoryId));
            product.addCategory(category);
        }
        if (productRequest.getBrandId().intValue() != product.getBrand().getId()) {
            Brand brand = brandRepository.findById(productRequest.getBrandId())
                    .orElseThrow(() -> new ResourceNotFoundException("Not found the category with id: " + productRequest.getBrandId()));
            product.setBrand(brand);
        }
        productRepository.save(product);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("ok", "Update product successfully", product));
    }

    @Transactional
    public ResponseEntity<ResponseObject> softDeleteOneOrManyProducts(List<Long> ids) {
        for (Long id : ids) {
            if (!productRepository.existsByIdAndDeleted(id, false)) {
                throw new ResourceNotFoundException("Not found user with id: " + id);
            }
        }
        productRepository.softDeleteAllByIds(ids);
        return ResponseEntity.ok(new ResponseObject("ok", "Delete users successfully", ""));

    }

    @Transactional
    public ResponseEntity<ResponseObject> forceDeleteOneOrManyProducts(List<Long> ids) {
        for (Long id : ids) {
            if (!productRepository.existsByIdAndDeleted(id, true)) {
                throw new ResourceNotFoundException("Not found user with id: " + id);
            }
        }
        productRepository.deleteAllById(ids);
        return ResponseEntity.ok(new ResponseObject("ok", "Delete users permanently", ""));
    }

    @Transactional
    public ResponseEntity<ResponseObject> restoreOneOrManyProducts(List<Long> ids) {
        for (Long id : ids) {
            if (!productRepository.existsByIdAndDeleted(id, true)) {
                throw new ResourceNotFoundException("Not found user with id: " + id);
            }
        }
        productRepository.restoreAllByIds(ids);
        return ResponseEntity.ok(new ResponseObject("ok", "Restore users successfully", ""));

    }

}
