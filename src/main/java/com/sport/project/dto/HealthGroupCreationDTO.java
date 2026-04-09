package com.sport.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * DTO для создания {@link com.sport.project.dao.entity.HealthGroupsEntity}
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HealthGroupCreationDTO {

    @JsonProperty(namespace = "name", required = true)
    private String name;

    @JsonProperty(namespace = "description", required = true)
    private String description;
}
