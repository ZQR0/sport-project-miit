package com.sport.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO для {@link com.sport.project.dao.entity.VisitsEntity}
 */
@Getter
@Setter
@Builder
public class VisitDTO {

    @JsonProperty(namespace = "id", required = true)
    private Integer id;

    @JsonProperty(namespace = "student_id", required = true)
    private Integer studentId;

    @JsonProperty(namespace = "student_login", required = true)
    private String studentLogin;

    @JsonProperty(namespace = "lesson_id", required = true)
    private Integer lessonId;

    @JsonProperty(namespace = "is_exists", required = true)
    private boolean isExists;
}
