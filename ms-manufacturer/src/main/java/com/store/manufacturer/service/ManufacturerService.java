package com.store.manufacturer.service;

import com.store.dto.ManufacturerDto;
import com.store.kafka.ManufacturerNotification;
import com.store.manufacturer.model.Manufacturer;
import com.store.manufacturer.kafka.ManufacturerProducer;
import com.store.manufacturer.mapper.ManufacturerMapper;
import com.store.manufacturer.repository.ManufacturerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManufacturerService {

    private final ManufacturerRepository repository;
    private final ManufacturerMapper mapper;
    private final ManufacturerProducer manufacturerProducer;

    public ManufacturerService(ManufacturerRepository repository, ManufacturerMapper mapper,
            ManufacturerProducer manufacturerProducer) {
        this.repository = repository;
        this.mapper = mapper;
        this.manufacturerProducer = manufacturerProducer;
    }

    public List<ManufacturerDto> getAllManufacturers() {
        return mapper.toDtos(repository.findAll());
    }

    public ManufacturerDto getManufacturerById(Long id) {
        return repository.findById(id).map(mapper::toDto).orElse(null);
    }

    public ManufacturerDto createManufacturer(ManufacturerDto dto) {
        Manufacturer saved = repository.save(mapper.toEntity(dto));

        String email = saved.getFounder()
                .trim()
                .toLowerCase()
                .replaceAll("\\s+", ".") + "@store.com";
        manufacturerProducer.sendManufacturerNotification(
                new ManufacturerNotification(
                        email,
                        saved.getName(),
                        saved.getFounder(),
                        saved.getCountry()));
        return mapper.toDto(saved);
    }

    public ManufacturerDto updateManufacturer(Long id, ManufacturerDto dto) {
        Manufacturer existing = repository.findById(id).orElseThrow();
        mapper.updateEntityFromDto(dto, existing); // Appel de la méthode MapStruct
        return mapper.toDto(repository.save(existing));
    }

    public void deleteManufacturer(Long id) {
        repository.deleteById(id);
    }
}