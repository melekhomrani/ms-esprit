package com.store.product.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "products")
public class Product {

    @Id
    private String id;

    private String name;
    private String manufacturerId;
    private Double price;
    private String category;
    private List<String> ingredients;
    private List<String> labels;
    private boolean inStock;
}