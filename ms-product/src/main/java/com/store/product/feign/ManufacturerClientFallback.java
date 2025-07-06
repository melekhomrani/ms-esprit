package com.store.product.feign;

import com.store.dto.ManufacturerDto;
import org.springframework.stereotype.Component;

@Component
public class ManufacturerClientFallback implements ManufacturerClient {

    @Override
    public ManufacturerDto getManufacturer(Long id) {
        System.err.println("Manufacturer service is down, fallback triggered for ID: " + id);
        return new ManufacturerDto(
            id,
            "Unavailable Manufacturer",
            "N/A",
            "Unknown"
        );
    }
}
