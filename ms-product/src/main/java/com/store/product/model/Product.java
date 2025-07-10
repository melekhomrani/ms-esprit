package com.store.product.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "products")
public class Product {

    @Id
    private String id;
    @Indexed(unique = true)
    private String name;
    private String manufacturerId;
    private Double price;
    private String category;
    private boolean inStock;
}