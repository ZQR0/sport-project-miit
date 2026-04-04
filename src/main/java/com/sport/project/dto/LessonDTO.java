package com.sport.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * DTO для {@link com.sport.project.dao.entity.LessonsEntity}
 */
@Getter
@Setter
@Builder
public class LessonDTO {

    @JsonProperty(namespace = "id", required = true)
    private Integer id;

    @JsonProperty(namespace = "discipline_id", required = true)
    private Integer disciplineId;

    @JsonProperty(namespace = "discipline_name", required = true)
    private String disciplineName;

    @JsonProperty(namespace = "date_of_lesson", required = true)
    private LocalDate dateOfLesson;

    @JsonProperty(namespace = "teacher_id", required = false)
    private Integer teacherId;

    @JsonProperty(namespace = "teacher_fsp", required = false)
    private String teacherFsp;
}
