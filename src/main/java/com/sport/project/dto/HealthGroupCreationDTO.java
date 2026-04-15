package com.sport.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * DTO для создания {@link com.sport.project.dao.entity.HealthGroupsEntity}
 */
@Schema(description = "Данные для создания медицинской группы")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HealthGroupCreationDTO {

    @Schema(description = "Название медицинской группы", example = "Основная", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty(namespace = "name", required = true)
    private String name;

    @Schema(description = "Описание медицинской группы", example = "Студенты без ограничений по здоровью", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty(namespace = "description", required = true)
    private String description;
}
