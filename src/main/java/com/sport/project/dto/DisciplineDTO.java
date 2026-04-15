package com.sport.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO для {@link com.sport.project.dao.entity.DisciplineEntity}
 */
@Schema(description = "Данные учебной дисциплины")
@Getter
@Setter
@Builder
public class DisciplineDTO {

    @Schema(description = "Уникальный идентификатор дисциплины", example = "1")
    @JsonProperty(namespace = "id", required = true)
    private Integer id;

    @Schema(description = "Название дисциплины", example = "Физическая культура")
    @JsonProperty(namespace = "name", required = true)
    private String name;
}
