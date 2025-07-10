package com.store.product.repository;

import com.store.product.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
    Product findByName(String name);
    Product findByManufacturerId(String manufacturerId);
    boolean existsByName(String name);
    boolean existsByManufacturerId(String manufacturerId);
}