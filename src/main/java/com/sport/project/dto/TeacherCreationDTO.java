package com.sport.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TeacherCreationDTO {

    @JsonProperty(namespace = "fsp", required = true)
    private String fsp;

    @JsonProperty(namespace = "login", required = true)
    private String login;

    @JsonProperty(namespace = "password", required = true)
    private String password;

    @JsonProperty(namespace = "is_moderator", required = true)
    private boolean isModerator;

}
