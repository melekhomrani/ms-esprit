package com.store.manufacturer.controller;

import com.store.dto.ManufacturerDto;
import com.store.manufacturer.service.ManufacturerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/manufacturers")
public class ManufacturerController {

    private final ManufacturerService service;

    public ManufacturerController(ManufacturerService service) {
        this.service = service;
    }

    @GetMapping
    public List<ManufacturerDto> getAllManufacturers() {
        return service.getAllManufacturers();
    }

    @GetMapping("/{id}")
    public ManufacturerDto getManufacturer(@PathVariable Long id) {
        return service.getManufacturerById(id);
    }

    @PostMapping
    public ManufacturerDto createManufacturer(@RequestBody ManufacturerDto dto) {
        return service.createManufacturer(dto);
    }

    @PutMapping("/{id}")
    public ManufacturerDto updateManufacturer(@PathVariable Long id, @RequestBody ManufacturerDto dto) {
        return service.updateManufacturer(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteManufacturer(@PathVariable Long id) {
        service.deleteManufacturer(id);
    }
}