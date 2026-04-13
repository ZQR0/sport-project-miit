package com.sport.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sport.project.utils.LocalTimeDeserializer;
import lombok.*;
import tools.jackson.databind.annotation.JsonDeserialize;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO для создания {@link com.sport.project.dao.entity.LessonsEntity}
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LessonCreationDTO {

    @JsonProperty(namespace = "date_of_lesson", required = true)
    private LocalDate dateOfLesson;

    @JsonProperty(namespace = "teacher_id", required = false)
    private Integer teacherId;

    @JsonProperty(namespace = "discipline_id", required = true)
    private Integer disciplineId;

    @JsonProperty(namespace = "start_at", required = true)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime startAt;

    @JsonProperty(namespace = "end_at", required = true)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime endAt;

}
