package com.sport.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sport.project.dao.entity.StudentEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * DTO for {@link com.sport.project.dao.entity.TeacherEntity}
 */

@Getter
@Setter
@Builder
public class TeacherDTO {

    @JsonProperty(namespace = "id", required = true)
    private Integer id;

    @JsonProperty(namespace = "fsp", required = true)
    private String fsp;

    @JsonProperty(namespace = "login", required = true)
    private String login;

    @JsonProperty(namespace = "password_hash", required = true)
    private String passwordHash;

    @JsonProperty(namespace = "is_moderator", required = true)
    private boolean isModerator;

    @JsonProperty(namespace = "schedule", required = true)
    private Map<LocalDate, String> schedule;

    @JsonProperty(namespace = "students", required = true)
    private List<StudentEntity> students;
}