package com.sport.project.controller.rest;

import com.sport.project.dto.*;
import com.sport.project.exception.EntityNotFoundException;
import com.sport.project.service.DisciplineService;
import com.sport.project.service.impl.DisciplineServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST контроллер для управления учебными дисциплинами
 */
@Tag(name = "Дисциплины", description = "API для управления учебными дисциплинами")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/discipline")
public class DisciplineController {
    private final DisciplineServiceImpl disciplineService;

    @Operation(summary = "Получить все дисциплины", description = "Возвращает список всех учебных дисциплин")
    @ApiResponse(responseCode = "200", description = "Дисциплины успешно получены")
    @GetMapping(path = "/find-all")
    public ResponseEntity<List<DisciplineDTO>> findAll() {
        return ResponseEntity.ok(disciplineService.findAll());
    }

    @Operation(summary = "Получить дисциплину по ID", description = "Возвращает учебную дисциплину по её уникальному идентификатору")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Дисциплина найдена"),
        @ApiResponse(responseCode = "404", description = "Дисциплина не найдена")
    })
    @GetMapping("/{id}")
    public ResponseEntity<DisciplineDTO> findById(
            @Parameter(description = "ID дисциплины", example = "1")
            @PathVariable(name = "id") Integer id) throws com.sport.project.exception.EntityNotFoundException {
        return ResponseEntity.ok(disciplineService.findById(id));
    }

    @Operation(summary = "Получить дисциплину по названию", description = "Возвращает учебную дисциплину по её названию")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Дисциплина найдена"),
        @ApiResponse(responseCode = "404", description = "Дисциплина не найдена")
    })
    @GetMapping("/by-name")
    public ResponseEntity<DisciplineDTO> findByName(
            @Parameter(description = "Название дисциплины", example = "Физическая культура")
            @RequestParam(name = "name") String name) throws com.sport.project.exception.EntityNotFoundException {
        return ResponseEntity.ok(disciplineService.findByName(name));
    }


    @Operation(summary = "Получить занятия по ID дисциплины", description = "Возвращает список занятий по ID учебной дисциплины")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Занятия найдены"),
        @ApiResponse(responseCode = "404", description = "Дисциплина не найдена")
    })
    @GetMapping("/by-discipline-id/{disciplineId}/lessons")
    public ResponseEntity<List<LessonDTO>> getLessonsByDisciplineId(
            @Parameter(description = "ID дисциплины", example = "1")
            @PathVariable(name = "disciplineId") Integer disciplineId) throws com.sport.project.exception.EntityNotFoundException {
        return ResponseEntity.ok(disciplineService.getLessons(disciplineId));
    }

    @Operation(summary = "Получить занятия по названию дисциплины", description = "Возвращает список занятий по названию учебной дисциплины")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Занятия найдены"),
        @ApiResponse(responseCode = "404", description = "Дисциплина не найдена")
    })
    @GetMapping("/by-discipline-name/{disciplineName}/lessons")
    public ResponseEntity<List<LessonDTO>> getLessons(
            @Parameter(description = "Название дисциплины", example = "Физическая культура")
            @PathVariable(name = "disciplineName") String disciplineName) throws EntityNotFoundException {
        return ResponseEntity.ok(disciplineService.getLessons(disciplineName));
    }

    @Operation(summary = "Проверить существование дисциплины по названию", description = "Проверяет существование дисциплины по названию")
    @ApiResponse(responseCode = "200", description = "Результат проверки")
    @GetMapping(path = "/is-exists-by-name")
    public boolean existsByName(
            @Parameter(description = "Название дисциплины", example = "Физическая культура")
            @RequestParam(name = "disciplineName") String disciplineName) {
        return this.disciplineService.existsByName(disciplineName);
    }

    @Operation(summary = "Создать дисциплину через DTO", description = "Создаёт новую учебную дисциплину")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Дисциплина успешно создана"),
        @ApiResponse(responseCode = "400", description = "Дисциплина с таким названием уже существует")
    })
    @PostMapping(path = "/create")
    public ResponseEntity<DisciplineDTO> create(
            @Parameter(description = "Данные для создания дисциплины")
            @RequestBody DisciplineCreationDTO dto) {
        DisciplineDTO created = disciplineService.create(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Создать дисциплину по названию", description = "Создаёт новую учебную дисциплину по названию")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Дисциплина успешно создана"),
        @ApiResponse(responseCode = "400", description = "Дисциплина с таким названием уже существует")
    })
    @PostMapping(path = "/create-by-name")
    public ResponseEntity<DisciplineDTO> create(
            @Parameter(description = "Название дисциплины")
            @RequestBody String name) {
        DisciplineDTO created = disciplineService.create(name);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
