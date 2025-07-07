package com.store.product.service;

import com.store.dto.ProductDto;
import com.store.product.mapper.ProductMapper;
import com.store.product.model.Product;
import com.store.product.repository.ProductRepository;
import com.store.dto.ManufacturerDto;
import com.store.product.feign.ManufacturerClient;
import com.store.product.kafka.ProductProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private ManufacturerClient manufacturerClient;

    @Mock
    private ProductProducer productProducer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateProduct() {
        ProductDto dto = new ProductDto(null, "TestProduct", "manufacturerId", 100.0, "category", true);
        Product product = new Product();

        when(productMapper.toEntity(dto)).thenReturn(product);
        when(productRepository.findByName(dto.getName())).thenReturn(null);
        when(manufacturerClient.getManufacturer(anyLong())).thenReturn(new ManufacturerDto());
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toDto(product)).thenReturn(dto);
        doNothing().when(productProducer).sendProductNotification(any());

        ProductDto result = productService.createProduct(dto);

        assertEquals(dto.getName(), result.getName());
    }

    @Test
    void shouldFindProductById() {
        Product product = new Product();
        product.setId("id");
        when(productRepository.findById("id")).thenReturn(Optional.of(product));

        ProductDto dto = new ProductDto("id", "TestProduct", "manufacturerId", 100.0, "category", true);
        when(productMapper.toDto(product)).thenReturn(dto);

        ProductDto result = productService.getProductById("id");
        assertEquals("id", result.getId());
    }
}