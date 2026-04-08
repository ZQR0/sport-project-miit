package com.sport.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;

/**
 * Creation DTO for {@link com.sport.project.dao.entity.TeacherEntity}
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeacherCreationDTO {

    @JsonProperty(namespace = "firstName", required = true)
    private String firstName;

    @JsonProperty(namespace = "lastName", required = true)
    private String lastName;

    @JsonProperty(namespace = "patronymic")
    private String patronymic;

    @JsonProperty(namespace = "login", required = true)
    private String login;

    @JsonProperty(namespace = "password", required = true)
    private String password;

    @JsonProperty(namespace = "moderator", required = true)
    private Boolean moderator;

    @JsonProperty(namespace = "birthday", required = false)
    private LocalDate birthday;

}
