package com.example.ElasticsearchSpringApp.repository;

import com.example.ElasticsearchSpringApp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsRepository extends JpaRepository<Product, Integer> {
    @Query("SELECT p FROM Product p WHERE p.active = true AND p.startDate <= CURRENT_DATE")
    List<Product> findAllActiveProducts();
}
