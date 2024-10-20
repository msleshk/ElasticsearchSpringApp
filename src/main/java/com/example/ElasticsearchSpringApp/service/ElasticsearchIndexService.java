package com.example.ElasticsearchSpringApp.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.DeleteResponse;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.example.ElasticsearchSpringApp.model.Product;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    public void removeInactiveProductsFromElasticsearch(List<Product> activeProducts) throws IOException {
        SearchResponse<Product> searchResponse = elasticsearchClient.search(s -> s
                .index("products")
                .query(q -> q.matchAll(m -> m)), Product.class);

        List<Product> elasticProducts = searchResponse.hits().hits().stream()
                .map(Hit::source)
                .toList();

        Set<Integer> activeProductIds = activeProducts.stream()
                .map(Product::getId)
                .collect(Collectors.toSet());

        for (Product elasticProduct : elasticProducts) {
            if (!activeProductIds.contains(elasticProduct.getId())) {
                deleteProductFromElasticsearch(elasticProduct.getId());
            }
        }
    }

    public void deleteProductFromElasticsearch(Integer productId) throws IOException {
        DeleteResponse deleteResponse = elasticsearchClient.delete(d -> d
                .index("products")
                .id(productId.toString()));
        System.out.println("Deleted product with ID: " + productId + ", result: " + deleteResponse.result());
    }
}
