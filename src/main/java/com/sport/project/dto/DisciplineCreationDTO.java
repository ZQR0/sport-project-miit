package com.sport.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO для создания {@link com.sport.project.dao.entity.DisciplineEntity}
 */
@Schema(description = "Данные для создания учебной дисциплины")
@Getter
@Setter
@Builder
public class DisciplineCreationDTO {

    @Schema(description = "Название дисциплины", example = "Физическая культура", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty(namespace = "name", required = true)
    private String name;
}
