package com.example.ElasticsearchSpringApp.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import com.example.ElasticsearchSpringApp.model.Product;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ElasticsearchIndexService {
    private final ElasticsearchClient elasticsearchClient;

    public ElasticsearchIndexService(ElasticsearchClient elasticsearchClient) {
        this.elasticsearchClient = elasticsearchClient;
    }

    public void indexProductWithSkus(Product product) throws IOException {
        IndexResponse response = elasticsearchClient.index(i -> i
                .index("products")
                .id(product.getId().toString())
                .document(product));
        System.out.println("Indexed product: " + product.getName() + ", version: " + response.version());
    }
}
