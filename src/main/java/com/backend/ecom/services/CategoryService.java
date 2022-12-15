package com.backend.ecom.services;

import com.backend.ecom.dto.category.CategoryRequestDTO;
import com.backend.ecom.dto.product.ProductShortInfoDTO;
import com.backend.ecom.entities.Category;
import com.backend.ecom.entities.Product;
import com.backend.ecom.exception.ResourceNotFoundException;
import com.backend.ecom.payload.request.ArrayRequest;
import com.backend.ecom.payload.response.ResponseObject;
import com.backend.ecom.repositories.CategoryRepository;
import com.backend.ecom.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public ResponseEntity<ResponseObject> getCategoryDetail(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found category with id: " + id));

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Query category successfully", category));
    }

    public ResponseEntity<ResponseObject> createCategory(CategoryRequestDTO categoryRequest) {
        boolean exist = categoryRepository.existsByName(categoryRequest.getName().trim());
        if (exist) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseObject("error", "Category name is already existed", categoryRequest.getName()));
        }
        Category category = new Category(categoryRequest);
        categoryRepository.save(category);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Create category successfully", category));
    }

    public ResponseEntity<ResponseObject> updateCategory(Long id, CategoryRequestDTO categoryRequest) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found category with id:" + id));
        boolean existName = categoryRepository.existsByName(categoryRequest.getName().trim());
        if (!category.getName().equals(categoryRequest.getName().trim()) && existName) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseObject("error", "Category name is already existed", ""));
        }
        category.setName(categoryRequest.getName());
        categoryRepository.save(category);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Update category successfully", category));

    }

    @Transactional
    public ResponseEntity<ResponseObject> deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Not found category with id:" + id);
        }
        List<Product> products = productRepository.findProductsByCategories_idAndDeleted(id, false);
        products.forEach(product -> {
            if (product.getCategories().size() > 1) {
                product.removeCategory(id);
                productRepository.save(product);
            } else if (product.getCategories().size() == 1) {
                productRepository.delete(product);
            }
        });
        categoryRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Delete category successfully", ""));

    }

}
