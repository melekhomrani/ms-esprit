package com.store.product.service;

import com.store.product.dto.ProductDto;
import com.store.product.mapper.ProductMapper;
import com.store.product.model.Product;
import com.store.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public ProductService(ProductRepository repository, ProductMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<ProductDto> getAllProducts() {
        return mapper.toDtos(repository.findAll());
    }

    public ProductDto getProductById(String id) {
        return repository.findById(id).map(mapper::toDto).orElse(null);
    }

    public ProductDto createProduct(ProductDto dto) {
        Product saved = repository.save(mapper.toEntity(dto));
        return mapper.toDto(saved);
    }

    public ProductDto updateProduct(String id, ProductDto dto) {
        Product existing = repository.findById(id).orElseThrow();
        mapper.updateEntityFromDto(dto, existing); // ✅
        return mapper.toDto(repository.save(existing));
    }

    public void deleteProduct(String id) {
        repository.deleteById(id);
    }

}