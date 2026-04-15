package com.sport.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sport.project.utils.LocalTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import tools.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO для {@link com.sport.project.dao.entity.LessonsEntity}
 */
@Schema(description = "Данные занятия")
@Getter
@Setter
@Builder
public class LessonDTO {

    @Schema(description = "Уникальный идентификатор занятия", example = "1")
    @JsonProperty(namespace = "id", required = true)
    private Integer id;

    @Schema(description = "ID учебной дисциплины", example = "2")
    @JsonProperty(namespace = "discipline_id", required = true)
    private Integer disciplineId;

    @Schema(description = "Название дисциплины", example = "Физическая культура")
    @JsonProperty(namespace = "discipline_name", required = true)
    private String disciplineName;

    @Schema(description = "Дата проведения занятия", example = "2024-09-01")
    @JsonProperty(namespace = "date_of_lesson", required = true)
    private LocalDate dateOfLesson;

    @Schema(description = "ID преподавателя, проводящего занятие", example = "3")
    @JsonProperty(namespace = "teacher_id")
    private Integer teacherId;

    @Schema(description = "ФИО преподавателя, проводящего занятие", example = "Петров Пётр Петрович")
    @JsonProperty(namespace = "teacher_full_name")
    private String teacherFullName;

    @Schema(description = "Время начала занятия", example = "10:00")
    @JsonProperty(namespace = "start_at")
    @JsonSerialize(using = LocalTimeSerializer.class)
    private LocalTime startAt;

    @Schema(description = "Время окончания занятия", example = "11:30")
    @JsonProperty(namespace = "end_at")
    @JsonSerialize(using = LocalTimeSerializer.class)
    private LocalTime endAt;

}
