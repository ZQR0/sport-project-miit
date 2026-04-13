package com.sport.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;

/**
 * DTO для создания {@link com.sport.project.dao.entity.LessonsEntity}
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LessonCreationDTO {

    //FIXME: вот надо подумать, стоит указывать ФИО препода или так оставить. То же самое с дисциплиной

    @JsonProperty(namespace = "date_of_lesson", required = true)
    private LocalDate dateOfLesson;

    @JsonProperty(namespace = "teacher_id", required = false)
    private Integer teacherId;

    @JsonProperty(namespace = "discipline_id", required = true)
    private Integer disciplineId;
}
