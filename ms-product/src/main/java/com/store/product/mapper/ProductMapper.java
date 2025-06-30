package com.store.product.mapper;

import com.store.product.dto.ProductDto;
import com.store.product.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {

    Product toEntity(ProductDto productDto);

    ProductDto toDto(Product product);

    // Méthode pour convertir une liste d'entités en liste de DTOs
    List<ProductDto> toDtos(List<Product> products);

    // Méthode pour mettre à jour une entité existante
    void updateEntityFromDto(ProductDto dto, @MappingTarget Product entity);
}