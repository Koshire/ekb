package com.ktaksv.ekb.model.history;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EntityType {

    USER_MODEL("Пользователь системы");

    public String getName() {
        return name;
    }

    public String getCode() {
        return this.name();
    }

    private String name;

    EntityType(String name) {
        this.name = name;
    }

    @JsonCreator
    public static EntityType fromJson(@JsonProperty("code") String code) {
        return valueOf(code);
    }
}
