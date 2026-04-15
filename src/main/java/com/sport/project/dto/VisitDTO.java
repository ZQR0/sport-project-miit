package com.sport.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO для {@link com.sport.project.dao.entity.VisitsEntity}
 */
@Schema(description = "Данные посещения занятия")
@Getter
@Setter
@Builder
public class VisitDTO {

    @Schema(description = "Уникальный идентификатор записи о посещении", example = "1")
    @JsonProperty(namespace = "id", required = true)
    private Integer id;

    @Schema(description = "ID студента", example = "5")
    @JsonProperty(namespace = "student_id", required = true)
    private Integer studentId;

    @Schema(description = "Логин студента", example = "ivanov")
    @JsonProperty(namespace = "student_login", required = true)
    private String studentLogin;

    @Schema(description = "ID занятия", example = "10")
    @JsonProperty(namespace = "lesson_id", required = true)
    private Integer lessonId;

    @Schema(description = "Флаг присутствия студента на занятии", example = "true")
    @JsonProperty(namespace = "is_exists", required = true)
    private boolean isExists;
}
