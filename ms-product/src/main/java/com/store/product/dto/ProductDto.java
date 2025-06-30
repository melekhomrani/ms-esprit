package com.store.product.dto;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private String id;
    private String name;
    private String manufacturerId;
    private Double price;
    private String category;
    private List<String> ingredients;
    private List<String> labels;
    private boolean inStock;
}