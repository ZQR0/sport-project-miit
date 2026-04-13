    package com.sport.project.dto;

    import com.fasterxml.jackson.annotation.JsonProperty;
    import lombok.*;

    import java.time.LocalDate;

    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class StudentCreationDTO {

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

        @JsonProperty(value = "health_group_id", required = true)
        private Integer healthGroupId;

        @JsonProperty(value = "group_id", required = true)
        private Integer groupId;

        @JsonProperty("birthday")
        private LocalDate birthday;

    }
