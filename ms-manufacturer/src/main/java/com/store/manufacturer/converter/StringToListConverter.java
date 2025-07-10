package com.store.manufacturer.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class StringToListConverter implements AttributeConverter<List<String>, String> {

    private static final String SEPARATOR = ",";

    @Override
    public String convertToDatabaseColumn(List<String> list) {
        return list != null ? String.join(SEPARATOR, list) : null;
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        return dbData != null 
            ? Arrays.stream(dbData.split(SEPARATOR)).collect(Collectors.toList()) 
            : null;
    }
}