package com.sport.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO для {@link com.sport.project.dao.entity.HealthGroupsEntity}
 */
@Schema(description = "Данные медицинской группы")
@Getter
@Setter
@Builder
public class HealthGroupDTO {

    @Schema(description = "Уникальный идентификатор медицинской группы", example = "1")
    @JsonProperty(namespace = "id", required = true)
    private Integer id;

    @Schema(description = "Название медицинской группы", example = "Основная")
    @JsonProperty(namespace = "name", required = true)
    private String name;

    @Schema(description = "Описание медицинской группы", example = "Студенты без ограничений по здоровью")
    @JsonProperty(namespace = "description", required = true)
    private String description;
}
