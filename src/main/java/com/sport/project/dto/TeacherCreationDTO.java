package com.sport.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

/**
 * DTO для создания {@link com.sport.project.dao.entity.TeacherEntity}
 */
@Schema(description = "Данные для создания преподавателя")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeacherCreationDTO {

    @Schema(description = "Имя преподавателя", example = "Пётр", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty(namespace = "firstName", required = true)
    private String firstName;

    @Schema(description = "Фамилия преподавателя", example = "Петров", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty(namespace = "lastName", required = true)
    private String lastName;

    @Schema(description = "Отчество преподавателя", example = "Петрович")
    @JsonProperty(namespace = "patronymic")
    private String patronymic;

    @Schema(description = "Логин преподавателя для входа в систему", example = "petrov", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty(namespace = "login", required = true)
    private String login;

    @Schema(description = "Пароль преподавателя", example = "password123", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty(namespace = "password", required = true)
    private String password;

    @Schema(description = "Флаг модератора (есть ли права модератора)", example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty(namespace = "moderator", required = true)
    private Boolean moderator;

    @Schema(description = "Дата рождения преподавателя", example = "1980-05-20")
    @JsonProperty(namespace = "birthday", required = false)
    private LocalDate birthday;

}
