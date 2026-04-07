package com.sport.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO для создания {@link com.sport.project.dao.entity.VisitsEntity}
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class VisitCreationDTO {

    //FIXME: подумать как лучше: айдишники или строковые поля

    @JsonProperty(namespace = "student_login", required = true)
    private String studentLogin;

    @JsonProperty(namespace = "lesson_id", required = true)
    private Integer lessonId;

    @JsonProperty(namespace = "is_exists", required = true)
    private boolean isExists;
}
