package com.sport.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * DTO для создания {@link com.sport.project.dao.entity.SectionEntity}
 */
@Schema(description = "Данные для создания спортивной секции")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SectionCreationDTO {

    @Schema(description = "Название спортивной секции", example = "Футбол", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty(namespace = "name", required = true)
    private String name;

    @Schema(description = "Описание спортивной секции", example = "Секция футбола для студентов всех курсов", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty(namespace = "description", required = true)
    private String description;
}
