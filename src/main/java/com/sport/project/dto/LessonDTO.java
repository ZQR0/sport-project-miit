package com.sport.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sport.project.utils.LocalTimeSerializer;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import tools.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDate;
import java.time.LocalTime;

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

    @JsonProperty(namespace = "teacher_id")
    private Integer teacherId;

    @JsonProperty(namespace = "teacher_full_name")
    private String teacherFullName;

    @JsonProperty(namespace = "start_at")
    @JsonSerialize(using = LocalTimeSerializer.class)
    private LocalTime startAt;

    @JsonProperty(namespace = "end_at")
    @JsonSerialize(using = LocalTimeSerializer.class)
    private LocalTime endAt;

}
