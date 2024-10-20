package com.example.ElasticsearchSpringApp.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.example.ElasticsearchSpringApp.model.Product;
import com.example.ElasticsearchSpringApp.model.Sku;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {

    @Value("${product.filter.enabled}")
    private boolean filterEnabled;

    @Value("${product.filter.color}")
    private String filterColor;

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
        if (!filterEnabled) {
            return products;
        } else {
            return products.stream()
                    .map(this::filterProductsBySkus)
                    .filter(product -> !product.getSkuList().isEmpty())
                    .collect(Collectors.toList());
        }

    }

    private Product filterProductsBySkus(Product product) {
        List<Sku> filteredSku = product.getSkuList().stream()
                .filter(sku -> filterColor.equalsIgnoreCase(sku.getColor()) && sku.getAvailability())
                .toList();

        product.setSkuList(filteredSku);
        return product;
    }
}
