package com.store.manufacturer.service;

import com.store.dto.ManufacturerDto;
import com.store.manufacturer.model.Manufacturer;
import com.store.manufacturer.mapper.ManufacturerMapper;
import com.store.manufacturer.repository.ManufacturerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManufacturerService {

    private final ManufacturerRepository repository;
    private final ManufacturerMapper mapper;

    public ManufacturerService(ManufacturerRepository repository, ManufacturerMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<ManufacturerDto> getAllManufacturers() {
        return mapper.toDtos(repository.findAll());
    }

    public ManufacturerDto getManufacturerById(Long id) {
        return repository.findById(id).map(mapper::toDto).orElse(null);
    }

    public ManufacturerDto createManufacturer(ManufacturerDto dto) {
        Manufacturer saved = repository.save(mapper.toEntity(dto));
        return mapper.toDto(saved);
    }

    public ManufacturerDto updateManufacturer(Long id, ManufacturerDto dto) {
        Manufacturer existing = repository.findById(id).orElseThrow();
        mapper.updateEntityFromDto(dto, existing);  // Appel de la méthode MapStruct
        return mapper.toDto(repository.save(existing));
    }
    public void deleteManufacturer(Long id) {
        repository.deleteById(id);
    }
}