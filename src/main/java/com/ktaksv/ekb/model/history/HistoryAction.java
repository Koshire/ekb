package com.ktaksv.ekb.model.history;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum HistoryAction {

    SAVE("Сохранено, создано"),
    UPDATE("Обновлено, изменено"),
    ENABLE("Активировано/деактивировано"),
    DELETE("Удалено");

    public String getName() {
        return name;
    }

    public String getCode() {
        return this.name();
    }

    private String name;

    HistoryAction(String name) {
        this.name = name;
    }

    @JsonCreator
    public static HistoryAction fromJson(@JsonProperty("code") String code) {
        return valueOf(code);
    }
}
