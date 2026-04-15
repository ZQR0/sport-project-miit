package com.sport.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sport.project.utils.StackTraceSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import tools.jackson.databind.annotation.JsonSerialize;

/**
 * DTO для отображения ошибок, использует сериализатор
 * {@link StackTraceSerializer} для отображения ошибок
 */
@Schema(description = "Информация об ошибке API")
@Builder
@Getter
@Setter
@AllArgsConstructor
public class ErrorResponseDto {

    @Schema(description = "HTTP код статуса ответа", example = "404")
    @JsonProperty(namespace = "status_code")
    private int statusCode;

    @Schema(description = "Сообщение об ошибке", example = "Entity not found")
    @JsonProperty(namespace = "message")
    private String message;

    @Schema(description = "Стек вызовов ошибки")
    @JsonProperty(namespace = "trace")
    @JsonSerialize(using = StackTraceSerializer.class)
    private StackTraceElement[] trace;

}
