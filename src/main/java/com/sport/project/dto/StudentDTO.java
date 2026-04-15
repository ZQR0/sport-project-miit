package com.sport.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Map;

/**
 * DTO for {@link com.sport.project.dao.entity.StudentEntity}
 */
@Schema(description = "Данные студента")
@Getter
@Setter
@Builder
public class StudentDTO {

    @Schema(description = "Уникальный идентификатор студента", example = "1")
    @JsonProperty(namespace = "id", required = true)
    private Integer id;

    @Schema(description = "Имя студента", example = "Иван")
    @JsonProperty(namespace = "firstName", required = true)
    private String firstName;

    @Schema(description = "Фамилия студента", example = "Иванов")
    @JsonProperty(namespace = "lastName", required = true)
    private String lastName;

    @Schema(description = "Отчество студента", example = "Иванович")
    @JsonProperty(namespace = "patronymic")
    private String patronymic;

    @Schema(description = "Логин студента для входа в систему", example = "ivanov")
    @JsonProperty(namespace = "login", required = true)
    private String login;

    @Schema(description = "ID медицинской группы", example = "2")
    @JsonProperty(namespace = "health_group", required = true)
    private Integer healthGroup;

    @Schema(description = "Статус посещения по датам (дата -> был ли студент)")
    @JsonProperty(namespace = "exist", required = true)
    private Map<LocalDate, Boolean> exist;
}