package com.sport.project.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jackson.JacksonComponent;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

import java.time.LocalTime;

/**
 * Класс для сериализации обычного времени в строку для JSON
 * @author Yaroslav
 * */
@JacksonComponent
@Slf4j
public class LocalTimeSerializer extends ValueSerializer<LocalTime> {
    @Override
    public void serialize(LocalTime value, JsonGenerator gen, SerializationContext context)
            throws JacksonException
    {
        if (value == null) {
            throw new IllegalArgumentException("Time value cannot be null");
        }
        String stringed = value.toString();
        log.info("Current local time value: {}", stringed);
        gen.writeString(stringed);
    }
}
