package com.ktaksv.ekb.model.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MapConverter extends AttributeJSONConverter<LinkedHashMap<Object, Object>> {

    private static final LinkedHashMap<Object, Object> list = new LinkedHashMap<>();

    @Override
    public LinkedHashMap<Object, Object> getInstance() {
        return list;
    }
}
