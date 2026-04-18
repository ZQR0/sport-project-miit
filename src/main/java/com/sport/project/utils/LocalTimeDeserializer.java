package com.sport.project.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jackson.JacksonComponent;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Десериализатор для LocalTime, чтобы в DTO отображалось всё корректно
 * */
@JacksonComponent
@Slf4j
public class LocalTimeDeserializer extends ValueDeserializer<LocalTime> {
    @Override
    public LocalTime deserialize(JsonParser p, DeserializationContext context) throws JacksonException {
        String timeStr = p.getValueAsString();
        log.info("Deserialized time {}", timeStr);
        if (timeStr == null || timeStr.isBlank()) return null;
        // Поддержка форматов "08:30" или "08:30:00"
        try {
            return LocalTime.parse(timeStr);
        } catch (Exception e) {
            // Попробуем с секундами
            return LocalTime.parse(timeStr, DateTimeFormatter.ofPattern("HH:mm:ss"));
        }
    }
}
