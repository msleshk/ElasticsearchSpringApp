package com.example.ElasticsearchSpringApp.controller;

import com.example.ElasticsearchSpringApp.model.Product;
import com.example.ElasticsearchSpringApp.service.SearchService;
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
    public List<Product> searchProducts(@RequestParam String keyword){
        return searchService.searchProducts(keyword);
    }
}
