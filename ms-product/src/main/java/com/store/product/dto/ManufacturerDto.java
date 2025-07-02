package com.store.product.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManufacturerDto {
    private Long id;
    private String name;
    private String founder;
    private String country;
}