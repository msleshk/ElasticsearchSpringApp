package com.example.ElasticsearchSpringApp.controller;

import com.example.ElasticsearchSpringApp.model.Product;
import com.example.ElasticsearchSpringApp.service.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DataSearchController {
    private final SearchService searchService;

    public DataSearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/search")
    @Operation(summary = "Search products in Elasticsearch", description = "Search products by keyword in name and description fields")
    public List<Product> searchProducts(@RequestParam String keyword) {
        return searchService.searchProducts(keyword);
    }
}
