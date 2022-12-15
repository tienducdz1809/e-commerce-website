package com.backend.ecom.controllers;

import com.backend.ecom.dto.product.ProductShortInfoDTO;
import com.backend.ecom.dto.user.UserShortInfoDTO;
import com.backend.ecom.services.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/v1/search")
public class SearchController {
    private static final Logger LOG = LoggerFactory.getLogger(SearchController.class);
    @Autowired
    private SearchService searchService;

    @PostMapping("/users")
    public List<UserShortInfoDTO> searchUser(@RequestParam(value = "q", defaultValue = "") String query,
                                             @RequestParam(value = "deleted", defaultValue = "false") Boolean deleted) {
        return searchService.searchUser(query, deleted);
    }

    @PostMapping("/products")
    public List<ProductShortInfoDTO> searchProduct(@RequestParam(value = "q", defaultValue = "") String query,
                                                   @RequestParam(value = "categories", defaultValue = "") String categories,
                                                   @RequestParam(value = "deleted", defaultValue = "false") Boolean deleted) {
        return searchService.searchProduct(query, categories, deleted);
    }
}
