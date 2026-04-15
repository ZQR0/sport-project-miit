package com.sport.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sport.project.utils.LocalTimeDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import tools.jackson.databind.annotation.JsonDeserialize;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO для создания {@link com.sport.project.dao.entity.LessonsEntity}
 */
@Schema(description = "Данные для создания занятия")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LessonCreationDTO {

    @Schema(description = "Дата проведения занятия", example = "2024-09-01", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty(namespace = "date_of_lesson", required = true)
    private LocalDate dateOfLesson;

    @Schema(description = "ID преподавателя, проводящего занятие", example = "3")
    @JsonProperty(namespace = "teacher_id", required = false)
    private Integer teacherId;

    @Schema(description = "ID учебной дисциплины", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty(namespace = "discipline_id", required = true)
    private Integer disciplineId;

    @Schema(description = "Время начала занятия", example = "10:00", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty(namespace = "start_at", required = true)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime startAt;

    @Schema(description = "Время окончания занятия", example = "11:30", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty(namespace = "end_at", required = true)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime endAt;

}
