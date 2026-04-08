package com.sport.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

/**
 * DTO for {@link com.sport.project.dao.entity.StudentEntity}
 */
@Getter
@Setter
@Builder
public class StudentDTO {

    //TODO: вывод group, sections и birthday

    @JsonProperty(namespace = "id", required = true)
    private Integer id;

    @JsonProperty(namespace = "firstName", required = true)
    private String firstName;

    @JsonProperty(namespace = "lastName", required = true)
    private String lastName;

    @JsonProperty(namespace = "patronymic")
    private String patronymic;

    @JsonProperty(namespace = "login", required = true)
    private String login;

    @JsonProperty(namespace = "health_group", required = true)
    private Integer healthGroup;

    @JsonProperty(namespace = "exist", required = true)
    private Map<LocalDate, Boolean> exist;
}