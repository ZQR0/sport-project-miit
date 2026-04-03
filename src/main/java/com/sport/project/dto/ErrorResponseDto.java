package com.sport.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sport.project.utils.StackTraceSerializer;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import tools.jackson.databind.annotation.JsonSerialize;

/**
 * DTO для отображения ошибок, использует сериализатор
 * {@link StackTraceSerializer} для отображения ошибок
 * */
@Builder
@Getter
@Setter
public class ErrorResponseDto {

    @JsonProperty(namespace = "message")
    private String message;

    @JsonProperty(namespace = "trace")
    @JsonSerialize(using = StackTraceSerializer.class)
    private StackTraceElement[] trace;

}
