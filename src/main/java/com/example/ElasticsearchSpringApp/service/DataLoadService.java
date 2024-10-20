package com.example.ElasticsearchSpringApp.service;

import com.example.ElasticsearchSpringApp.model.Product;
import com.example.ElasticsearchSpringApp.repository.ProductsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
public class DataLoadService {
    private final ProductsRepository productsRepository;
    private final ElasticsearchIndexService indexService;


    public DataLoadService(ProductsRepository productsRepository, ElasticsearchIndexService indexService) {
        this.productsRepository = productsRepository;
        this.indexService = indexService;
    }

    public void loadDataToElasticsearch() throws IOException {
        List<Product> filteredProducts = productsRepository.findAllActiveProducts();

        for (Product product : filteredProducts){
            indexService.indexProductWithSkus(product);
        }
    }
}
