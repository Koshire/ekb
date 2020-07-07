package com.ktaksv.ekb.model.history;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.ktaksv.ekb.model.converter.MapConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "history")
public class HistoryLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "ENTITY_TYPE")
    private EntityType entityType;

    @Column(name = "ENTITY_ID")
    private Long entityId;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "HISTORY_ACTION")
    private HistoryAction historyAction;

    @Column(name = "INITIATOR")
    private String initiator;

    @Column(name = "DATE_IN")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime date;

    @Column(name = "FIELDS")
    @Convert(converter = MapConverter.class)
    private Object fields;
}
