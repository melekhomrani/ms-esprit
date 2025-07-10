package com.store.manufacturer.mapper;

import com.store.dto.ManufacturerDto;
import com.store.manufacturer.model.Manufacturer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;
import org.mapstruct.BeanMapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ManufacturerMapper {

    ManufacturerMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(ManufacturerMapper.class);

    ManufacturerDto toDto(Manufacturer manufacturer);
    Manufacturer toEntity(ManufacturerDto manufacturerDto);

    List<ManufacturerDto> toDtos(List<Manufacturer> manufacturers);
    List<Manufacturer> toEntities(List<ManufacturerDto> dtos);

    // Prevent nulls in DTO from overriding existing entity fields
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(ManufacturerDto manufacturerDto, @MappingTarget Manufacturer manufacturer);
}
