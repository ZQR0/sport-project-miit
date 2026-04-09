package com.sport.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO для создания {@link com.sport.project.dao.entity.GroupEntity}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupCreationDTO {

    @JsonProperty(value = "name", required = true)
    private String name;

    @JsonProperty(value = "institute", required = true)
    private String institute;
}
