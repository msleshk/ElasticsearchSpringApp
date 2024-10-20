package com.example.ElasticsearchSpringApp.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.example.ElasticsearchSpringApp.model.Product;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class SearchService {

    private final ElasticsearchClient elasticsearchClient;

    public SearchService(ElasticsearchClient elasticsearchClient) {
        this.elasticsearchClient = elasticsearchClient;
    }

    public List<Product> searchProducts(String keyword) {
        List<Product> products = new ArrayList<>();
        try {
            SearchResponse<Product> response = elasticsearchClient.search(s -> s
                            .index("products")
                            .query(q -> q
                                    .multiMatch(m -> m
                                            .fields("name", "description")
                                            .query(keyword)
                                    )
                            ),
                    Product.class
            );

            for (Hit<Product> hit : response.hits().hits()) {
                products.add(hit.source());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Product product : products){
            System.out.println(product);
        }
        return products;
    }
}
