package com.store.product.feign;

import com.store.dto.ManufacturerDto;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
    name = "ms-manufacturer",
    fallback = ManufacturerClientFallback.class,
    configuration = FeignConfig.class
)
public interface ManufacturerClient {

    @GetMapping("/api/manufacturers/{id}")
    @CircuitBreaker(name = "manufacturerClient")
    ManufacturerDto getManufacturer(@PathVariable("id") Long id);
}
