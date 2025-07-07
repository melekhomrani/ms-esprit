package com.store.product.config;

import static org.mockito.Mockito.*;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.Jwt;

@TestConfiguration
public class TestSecurityConfig {

    @Bean
    public JwtDecoder jwtDecoder() {
        Jwt jwt = Jwt.withTokenValue("token")
                .header("alg", "none")
                .claim("sub", "test")
                .build();
        JwtDecoder decoder = mock(JwtDecoder.class);
        when(decoder.decode(anyString())).thenReturn(jwt);
        return decoder;
    }
}
