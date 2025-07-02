package com.store.product.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.store.product.dto.ManufacturerDto;

@FeignClient(name = "ms-manufacturer")
public interface ManufacturerClient {

    @GetMapping("/api/manufacturers/{id}")
    ManufacturerDto getManufacturer(@PathVariable("id") Long id);
}
