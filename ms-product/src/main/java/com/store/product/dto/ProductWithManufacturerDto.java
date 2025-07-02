package com.store.product.dto;

import lombok.Data;

@Data
public class ProductWithManufacturerDto {
    private String id;
    private String name;
    private Double price;
    private String category;
    private boolean inStock;

    private ManufacturerDto manufacturer;
}
