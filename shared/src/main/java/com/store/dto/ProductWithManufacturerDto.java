package com.store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductWithManufacturerDto {
    private String id;
    private String name;
    private Double price;
    private String category;
    private boolean inStock;

    private ManufacturerDto manufacturer;
}
