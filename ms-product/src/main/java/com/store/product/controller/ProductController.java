package com.store.product.controller;


import com.store.dto.ManufacturerDto;
import com.store.dto.ProductDto;
import com.store.dto.ProductWithManufacturerDto;
import com.store.product.feign.ManufacturerClient;
import com.store.product.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;
    private final ManufacturerClient manufacturerClient;

    public ProductController(ProductService service, ManufacturerClient manufacturerClient) {
        this.service = service;
        this.manufacturerClient = manufacturerClient;
    }

    @GetMapping
    public List<ProductDto> getAllProducts() {
        return service.getAllProducts();
    }

    @GetMapping("/with-manufacturer")
    public List<ProductWithManufacturerDto> getProductsWithManufacturers() {
        return service.getProductsWithManufacturers();
    }

    @GetMapping("/{id}")
    public ProductDto getProduct(@PathVariable String id) {
        return service.getProductById(id);
    }

    @PostMapping
    public ProductDto createProduct(@RequestBody ProductDto dto) {
        return service.createProduct(dto);
    }

    @PutMapping("/{id}")
    public ProductDto updateProduct(@PathVariable String id, @RequestBody ProductDto dto) {
        return service.updateProduct(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable String id) {
        service.deleteProduct(id);
    }

    @GetMapping("/test-fallback")
    public ManufacturerDto testFallback() {
        return manufacturerClient.getManufacturer(123L);
    }
  
}