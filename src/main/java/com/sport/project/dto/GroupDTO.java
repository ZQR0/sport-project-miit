package com.sport.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO для {@link com.sport.project.dao.entity.GroupEntity}
 */
@Schema(description = "Данные учебной группы")
@Getter
@Setter
@Builder
public class GroupDTO {

    @Schema(description = "Уникальный идентификатор группы", example = "1")
    @JsonProperty(namespace = "id", required = true)
    private Integer id;

    @Schema(description = "Название группы", example = "ИУ7-12Б")
    @JsonProperty(namespace = "name", required = true)
    private String name;

    @Schema(description = "Название института", example = "ИУ")
    @JsonProperty(namespace = "institute", required = true)
    private String institute;
}
