package com.sport.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO для создания {@link com.sport.project.dao.entity.VisitsEntity}
 */
@Schema(description = "Данные для создания записи о посещении")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class VisitCreationDTO {

    @Schema(description = "Логин студента", example = "ivanov", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty(namespace = "student_login", required = true)
    private String studentLogin;

    @Schema(description = "ID занятия", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty(namespace = "lesson_id", required = true)
    private Integer lessonId;

    @Schema(description = "Флаг присутствия студента на занятии", example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty(namespace = "is_exists", required = true)
    private boolean isExists;
}
