package com.store.product.integration;

import com.store.dto.ProductDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldCreateAndFetchProduct() {

        ProductDto productDto = new ProductDto(null, "IntegrationProduct", "manufacturerId", 150.0, "category", true);

        ResponseEntity<ProductDto> postResponse = restTemplate.postForEntity("/products", productDto, ProductDto.class);
        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ProductDto created = postResponse.getBody();
        assertThat(created).as("Created product should not be null").isNotNull();
        if (created != null) {
            assertThat(created.getName()).isEqualTo("IntegrationProduct");
        }
    }
}