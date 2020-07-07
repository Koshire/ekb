package com.ktaksv.ekb.model.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import java.io.IOException;
import java.util.Optional;

public abstract class AttributeJSONConverter<T> implements AttributeConverter<T, String> {

    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    public abstract T getInstance();

    @Override
    public String convertToDatabaseColumn(T attribute) {
        Optional<T> object = Optional.ofNullable(attribute);
        String value = null;
        if (object.isPresent()) {
            try {
                value = objectMapper.writeValueAsString(attribute);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return value;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T convertToEntityAttribute(String dbData) {
        T map = null;
        Optional<String> string = Optional.ofNullable(dbData);
        if (string.isPresent()) {
            try {
                map = objectMapper.readValue(dbData, (Class<T>) getInstance().getClass());
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
        return map;
    }
}
