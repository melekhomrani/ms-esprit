package com.store.product;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import com.store.product.config.TestSecurityConfig;

@SpringBootTest(properties = {
        "spring.cloud.config.enabled=false",
        "eureka.client.enabled=false"
})
@Import(TestSecurityConfig.class)
class ProductServiceApplicationTest {

    @Test
    void contextLoads() {
    }
}
