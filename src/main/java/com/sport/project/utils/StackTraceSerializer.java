package com.sport.project.utils;

import org.springframework.boot.jackson.JacksonComponent;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

import java.util.Arrays;
import java.util.List;

/**
 * Сериализатор для того, чтобы StackTrace нормально выводился в JSON в REST-controller'ах
 * при ошибках
 * @author Yaroslav
 * @version 0.1.0
 * */
@JacksonComponent
public class StackTraceSerializer extends ValueSerializer<StackTraceElement[]> {
    @Override
    public void serialize(StackTraceElement[] value,
                          JsonGenerator gen,
                          SerializationContext context)
            throws JacksonException
    {
        if (value != null) {
            gen.writeStartArray();

            List<String> traces = Arrays.stream(value)
                    .map(String::valueOf)
                    .toList();

            for (String trace : traces) {
                gen.writeString(trace);
            }

            gen.writeEndArray();
        }
    }
}
