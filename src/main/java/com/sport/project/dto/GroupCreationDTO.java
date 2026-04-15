package com.sport.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO для создания {@link com.sport.project.dao.entity.GroupEntity}
 */
@Schema(description = "Данные для создания учебной группы")
@Getter
@Setter
@Builder
public class GroupCreationDTO {

    @Schema(description = "Название группы", example = "ИУ7-12Б", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty(namespace = "name", required = true)
    private String name;

    @Schema(description = "Название института", example = "ИУ", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty(namespace = "institute", required = true)
    private String institute;
}
