package com.sport.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * DTO для {@link com.sport.project.dao.entity.SectionEntity}
 */
@Schema(description = "Данные спортивной секции")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SectionDTO {

    @Schema(description = "Уникальный идентификатор секции", example = "1")
    @JsonProperty(namespace = "id", required = true)
    private Integer id;

    @Schema(description = "Название спортивной секции", example = "Футбол")
    @JsonProperty(namespace = "name", required = true)
    private String name;

    @Schema(description = "Описание спортивной секции", example = "Секция футбола для студентов всех курсов")
    @JsonProperty(namespace = "description", required = true)
    private String description;
}
