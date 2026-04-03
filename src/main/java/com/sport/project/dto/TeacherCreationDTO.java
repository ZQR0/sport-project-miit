package com.sport.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TeacherCreationDTO {

    @JsonProperty(namespace = "firstName", required = true)
    private String firstName;

    @JsonProperty(namespace = "secondName", required = true)
    private String secondName;

    @JsonProperty(namespace = "patronymic")
    private String patronymic;

    @JsonProperty(namespace = "login", required = true)
    private String login;

    @JsonProperty(namespace = "password", required = true)
    private String password;

    private Boolean isModerator;

}
