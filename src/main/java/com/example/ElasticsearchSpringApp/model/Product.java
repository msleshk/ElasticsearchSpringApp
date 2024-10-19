package com.example.ElasticsearchSpringApp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer id;



    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
