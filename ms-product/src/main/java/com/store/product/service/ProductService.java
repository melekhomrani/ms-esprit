package com.store.product.service;

import com.store.dto.ManufacturerDto;
import com.store.dto.ProductDto;
import com.store.dto.ProductWithManufacturerDto;
import com.store.kafka.ProductNotification;
import com.store.product.feign.ManufacturerClient;
import com.store.product.kafka.ProductProducer;
import com.store.product.mapper.ProductMapper;
import com.store.product.model.Product;
import com.store.product.repository.ProductRepository;

import feign.FeignException;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;
    private final ManufacturerClient manufacturerClient;
    private final ProductProducer productProducer;

    public ProductService(ProductRepository repository, ProductMapper mapper, ManufacturerClient manufacturerClient,
            ProductProducer productProducer) {
        this.repository = repository;
        this.mapper = mapper;
        this.manufacturerClient = manufacturerClient;
        this.productProducer = productProducer;
    }

    public List<ProductDto> getAllProducts() {
        return mapper.toDtos(repository.findAll());
    }

    public List<ProductWithManufacturerDto> getProductsWithManufacturers() {
        List<Product> products = repository.findAll();

        return products.stream().map(product -> {
            ManufacturerDto manufacturer = null;
            try {
                manufacturer = manufacturerClient.getManufacturer(Long.valueOf(product.getManufacturerId()));
            } catch (FeignException e) {
                // Log and continue with null manufacturer
                System.err.println("⚠️ Manufacturer not found for ID " + product.getManufacturerId());
            }

            ProductWithManufacturerDto dto = new ProductWithManufacturerDto();
            dto.setId(product.getId());
            dto.setName(product.getName());
            dto.setPrice(product.getPrice());
            dto.setCategory(product.getCategory());
            dto.setInStock(product.isInStock());
            dto.setManufacturer(manufacturer);

            return dto;
        }).toList();
    }

    public ProductDto getProductById(String id) {
        return repository.findById(id).map(mapper::toDto).orElse(null);
    }

    public ProductDto createProduct(ProductDto dto) {

        System.out.println("Fetching manufacturer by ID: " + dto.getManufacturerId());
        ManufacturerDto manufacturer = manufacturerClient.getManufacturer(Long.valueOf(dto.getManufacturerId()));
        System.out.println("Fetched manufacturer: " + manufacturer);

        if (manufacturer == null) {
            throw new IllegalArgumentException("Manufacturer with ID " + dto.getManufacturerId()
                    + " not found. \n Please ensure the manufacturer exists before creating a product.");
        }

        if (repository.findByName(dto.getName()) != null) {
            throw new IllegalArgumentException("Product with name '" + dto.getName() + "' already exists.");
        }

        System.out.println("Manufacturer found: " + manufacturer.getName());
        Product saved = repository.save(mapper.toEntity(dto));

        String email = manufacturer.getFounder()
                .trim()
                .toLowerCase()
                .replaceAll("\\s+", ".") + "@store.com";

        productProducer.sendProductNotification(
                new ProductNotification(
                        email,
                        saved.getName(),
                        manufacturer.getFounder(),
                        saved.getPrice(),
                        saved.getCategory(),
                        saved.isInStock()));

        return mapper.toDto(saved);
    }

    public ProductDto updateProduct(String id, ProductDto dto) {
        Product existing = repository.findById(id).orElseThrow();

        if (repository.findByName(dto.getName()) != null) {
            throw new IllegalArgumentException("Product with name '" + dto.getName() + "' already exists.");
        }

        mapper.updateEntityFromDto(dto, existing); // ✅
        return mapper.toDto(repository.save(existing));
    }

    public void deleteProduct(String id) {
        repository.deleteById(id);
    }

}