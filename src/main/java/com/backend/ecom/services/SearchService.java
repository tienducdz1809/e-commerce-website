package com.backend.ecom.services;

import com.backend.ecom.dto.product.ProductShortInfoDTO;
import com.backend.ecom.dto.user.UserShortInfoDTO;
import com.backend.ecom.entities.Product;
import com.backend.ecom.entities.User;
import com.backend.ecom.repositories.ProductRepository;
import com.backend.ecom.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public List<UserShortInfoDTO> searchUser(String query, Boolean deleted) {
        List<UserShortInfoDTO> userShortInfo = new ArrayList<>();

        if (query.equals("")) {
            List<User> users = userRepository.findAllByDeleted(deleted);
            users.forEach(user -> userShortInfo.add(new UserShortInfoDTO(user)));
        } else {
            List<User> users = userRepository.searchUser(query, deleted);
            users.forEach(user -> userShortInfo.add(new UserShortInfoDTO(user)));
        }
        return userShortInfo;
    }

    public List<ProductShortInfoDTO> searchProduct(String query, String categories, Boolean deleted) {
        List<ProductShortInfoDTO> productShortInfo = new ArrayList<>();
        if (query.equals("") && categories.equals("")) {
            List<Product> products = productRepository.findAllByDeleted(deleted);
            products.forEach(product -> productShortInfo.add(new ProductShortInfoDTO(product)));
        } else {
            List<Product> products = productRepository.searchProduct(query, categories, deleted);
            products.forEach(product -> productShortInfo.add(new ProductShortInfoDTO(product)));
        }
        return productShortInfo;
    }

}
