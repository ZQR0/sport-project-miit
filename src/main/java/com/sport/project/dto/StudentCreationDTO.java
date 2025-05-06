package com.sport.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class StudentCreationDTO {

    @JsonProperty(namespace = "fsp", required = true)
    private String fsp;

    @JsonProperty(namespace = "login", required = true)
    private String login;

    @JsonProperty(namespace = "password", required = true)
    private String password;

    @JsonProperty(namespace = "health_group", required = true)
    private Integer healthGroup;

    @JsonProperty(namespace = "teacher_id", required = true)
    private Integer teacherId;

}
