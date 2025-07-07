package com.store.product.controller;

import com.store.dto.ProductDto;
import com.store.product.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldGetAllProducts() throws Exception {
        ProductDto productDto1 = new ProductDto(
                "1",
                "TestProduct",
                "manufacturerId",
                100.0,
                "category",
                true);
        ProductDto productDto2 = new ProductDto(
                "2",
                "TestProduct2",
                "manufacturerId2",
                200.0,
                "category2",
                false);

        List<ProductDto> products = List.of(productDto1, productDto2);

        Mockito.when(productService.getAllProducts()).thenReturn(products);

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].name").value("TestProduct"))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andExpect(jsonPath("$[1].name").value("TestProduct2"));
    }

    @Test
    void shouldCreateProduct() throws Exception {
        ProductDto productDto = new ProductDto(
                null,
                "TestProduct",
                "manufacturerId",
                100.0,
                "category",
                true);

        Mockito.when(productService.createProduct(Mockito.any(ProductDto.class)))
                .thenReturn(productDto);

        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("TestProduct"));
    }
}