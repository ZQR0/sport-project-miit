package com.sport.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * DTO for {@link com.sport.project.dao.entity.TeacherEntity}
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDTO {

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

    @JsonProperty(namespace = "password_hash", required = true)
    private String passwordHash;

    @JsonProperty(namespace = "moderator", required = true)
    private boolean moderator;

    @JsonProperty(namespace = "schedule", required = true)
    private Map<LocalDate, String> schedule;

    @JsonProperty(namespace = "students", required = true)
    private List<StudentDTO> students;
}