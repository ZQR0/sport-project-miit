package com.sport.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * DTO для создания {@link com.sport.project.dao.entity.GroupEntity}
 */
@Schema(description = "Данные для создания учебной группы")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupCreationDTO {
    
    @Schema(description = "Название группы", example = "ИУ7-12Б", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty(value = "name", required = true)
    private String name;
    
    @Schema(description = "Название института", example = "ИУ", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty(value = "institute", required = true)
    private String institute;
}