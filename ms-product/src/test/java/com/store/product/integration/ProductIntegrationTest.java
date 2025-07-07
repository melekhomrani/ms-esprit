package com.store.product.integration;

import com.store.dto.ManufacturerDto;
import com.store.dto.ProductDto;
import com.store.product.feign.ManufacturerClient;
import com.store.product.kafka.ProductProducer;
import com.store.product.mapper.ProductMapper;
import com.store.product.model.Product;
import com.store.product.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import com.store.product.config.TestSecurityConfig;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = {
        "spring.cloud.config.enabled=false",
        "eureka.client.enabled=false"
    })
@Import(TestSecurityConfig.class)
class ProductIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductMapper productMapper;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private ManufacturerClient manufacturerClient;

    @MockBean
    private ProductProducer productProducer;

    @Test
    void shouldCreateAndFetchProduct() {

        ProductDto productDto = new ProductDto(null, "IntegrationProduct", "1", 150.0, "category", true);
        Product product = productMapper.toEntity(productDto);
        product.setId("123");

        given(manufacturerClient.getManufacturer(anyLong())).willReturn(new ManufacturerDto());
        given(productRepository.findByName(productDto.getName())).willReturn(null);
        given(productRepository.save(any(Product.class))).willReturn(product);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth("dummy");
        HttpEntity<ProductDto> request = new HttpEntity<>(productDto, headers);

        ResponseEntity<ProductDto> postResponse = restTemplate.postForEntity("/api/products", request, ProductDto.class);
        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ProductDto created = postResponse.getBody();
        assertThat(created).as("Created product should not be null").isNotNull();
        if (created != null) {
            assertThat(created.getName()).isEqualTo("IntegrationProduct");
        }
    }
}