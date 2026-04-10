package com.sport.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * DTO для {@link com.sport.project.dao.entity.SectionEntity}
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SectionDTO {

    @JsonProperty(namespace = "id", required = true)
    private Integer id;

    @JsonProperty(namespace = "name", required = true)
    private String name;

    @JsonProperty(namespace = "description", required = true)
    private String description;
}
