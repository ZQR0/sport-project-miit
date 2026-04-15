package com.sport.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

/**
 * DTO для создания {@link com.sport.project.dao.entity.StudentEntity}
 */
@Schema(description = "Данные для создания студента")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentCreationDTO {

    @Schema(description = "Имя студента", example = "Иван", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty(namespace = "firstName", required = true)
    private String firstName;

    @Schema(description = "Фамилия студента", example = "Иванов", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty(namespace = "lastName", required = true)
    private String lastName;

    @Schema(description = "Отчество студента", example = "Иванович")
    @JsonProperty(namespace = "patronymic")
    private String patronymic;

    @Schema(description = "Логин студента для входа в систему", example = "ivanov", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty(namespace = "login", required = true)
    private String login;

    @Schema(description = "Пароль студента", example = "password123", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty(namespace = "password", required = true)
    private String password;

    @Schema(description = "ID медицинской группы", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty(value = "health_group_id", required = true)
    private Integer healthGroupId;

    @Schema(description = "ID учебной группы", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty(value = "group_id", required = true)
    private Integer groupId;

    @Schema(description = "Дата рождения студента", example = "2000-01-15")
    @JsonProperty("birthday")
    private LocalDate birthday;

}
