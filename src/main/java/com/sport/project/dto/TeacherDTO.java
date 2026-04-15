package com.sport.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * DTO for {@link com.sport.project.dao.entity.TeacherEntity}
 */
@Schema(description = "Данные преподавателя")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDTO {

    @Schema(description = "Уникальный идентификатор преподавателя", example = "1")
    @JsonProperty(namespace = "id", required = true)
    private Integer id;

    @Schema(description = "Имя преподавателя", example = "Пётр")
    @JsonProperty(namespace = "firstName", required = true)
    private String firstName;

    @Schema(description = "Фамилия преподавателя", example = "Петров")
    @JsonProperty(namespace = "lastName", required = true)
    private String lastName;

    @Schema(description = "Отчество преподавателя", example = "Петрович")
    @JsonProperty(namespace = "patronymic")
    private String patronymic;

    @Schema(description = "Логин преподавателя для входа в систему", example = "petrov")
    @JsonProperty(namespace = "login", required = true)
    private String login;

    @Schema(description = "Хэш пароля преподавателя", example = "$2a$10$...")
    @JsonProperty(namespace = "password_hash", required = true)
    private String passwordHash;

    @Schema(description = "Флаг модератора (есть ли права модератора)", example = "true")
    @JsonProperty(namespace = "moderator", required = true)
    private boolean moderator;

    @Schema(description = "Расписание занятий (дата -> описание занятия)")
    @JsonProperty(namespace = "schedule", required = true)
    private Map<LocalDate, String> schedule;

    @Schema(description = "Список студентов, закреплённых за преподавателем")
    @JsonProperty(namespace = "students", required = true)
    private List<StudentDTO> students;
}