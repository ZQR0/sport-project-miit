package com.sport.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO для {@link com.sport.project.dao.entity.SectionEntity}
 */
@Getter
@Setter
@Builder
public class SectionDTO {

    @JsonProperty(namespace = "id", required = true)
    private Integer id;

    @JsonProperty(namespace = "name", required = true)
    private String name;

    @JsonProperty(namespace = "description", required = true)
    private String description;
}
