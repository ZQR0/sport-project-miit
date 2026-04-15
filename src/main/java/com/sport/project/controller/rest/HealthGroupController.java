package com.sport.project.controller.rest;

import com.sport.project.dto.HealthGroupCreationDTO;
import com.sport.project.dto.HealthGroupDTO;
import com.sport.project.dto.StudentDTO;
import com.sport.project.exception.EntityAlreadyExistsException;
import com.sport.project.service.impl.HealthGroupServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST контроллер для управления медицинскими группами
 */
@Tag(name = "Медицинские группы", description = "API для управления медицинскими группами")
@RestController
@RequestMapping("/api/healthGroup")
@RequiredArgsConstructor
public class HealthGroupController {

    private final HealthGroupServiceImpl healthGroupService;

    @Operation(summary = "Получить медицинскую группу по ID", description = "Возвращает медицинскую группу по её уникальному идентификатору")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Группа найдена"),
        @ApiResponse(responseCode = "404", description = "Группа не найдена")
    })
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> findByID(
            @Parameter(description = "ID медицинской группы", example = "1")
            @PathVariable(name = "id") Integer id) throws EntityNotFoundException {
        HealthGroupDTO healthGroup = healthGroupService.findById(id);
        return ResponseEntity.ok(healthGroup);
    }

    @Operation(summary = "Получить медицинскую группу по названию", description = "Возвращает медицинскую группу по её названию")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Группа найдена"),
        @ApiResponse(responseCode = "404", description = "Группа не найдена")
    })
    @GetMapping("/name/{name}")
    public ResponseEntity<?> findByName(
            @Parameter(description = "Название медицинской группы", example = "Основная")
            @PathVariable(name = "name") String name) throws EntityNotFoundException {
        HealthGroupDTO healthGroup = healthGroupService.findByName(name);
        return ResponseEntity.ok(healthGroup);
    }

    @Operation(summary = "Получить все медицинские группы", description = "Возвращает список всех медицинских групп")
    @ApiResponse(responseCode = "200", description = "Группы успешно получены")
    @GetMapping(path = "/find-all")
    public ResponseEntity<?> findAll() {
        List<HealthGroupDTO> healthGroups = healthGroupService.findAll();
        return ResponseEntity.ok(healthGroups);
    }

    @Operation(summary = "Получить студентов медицинской группы по ID", description = "Возвращает список студентов медицинской группы по её ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Студенты найдены"),
        @ApiResponse(responseCode = "404", description = "Группа не найдена")
    })
    @GetMapping(path = "/{id}/students")
    public ResponseEntity<?> getStudentsById(
            @Parameter(description = "ID медицинской группы", example = "1")
            @PathVariable(name = "id") Integer healthGroupId)
            throws EntityNotFoundException {
        List<StudentDTO> students = healthGroupService.getStudents(healthGroupId);
        return ResponseEntity.ok(students);
    }

    @Operation(summary = "Получить студентов медицинской группы по названию", description = "Возвращает список студентов медицинской группы по её названию")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Студенты найдены"),
        @ApiResponse(responseCode = "404", description = "Группа не найдена")
    })
    @GetMapping(path = "/name/{name}/students")
    public ResponseEntity<?> getStudentsByName(
            @Parameter(description = "Название медицинской группы", example = "Основная")
            @PathVariable(name = "name") String healthGroupName)
            throws EntityNotFoundException {
        List<StudentDTO> students = healthGroupService.getStudents(healthGroupName);
        return ResponseEntity.ok(students);
    }

    @Operation(summary = "Проверить существование группы по названию", description = "Проверяет существование медицинской группы по названию")
    @ApiResponse(responseCode = "200", description = "Результат проверки")
    @GetMapping("/exists/name/{name}")
    public ResponseEntity<?> existsByName(
            @Parameter(description = "Название медицинской группы", example = "Основная")
            @PathVariable(name = "name") String name) {
        boolean exists = healthGroupService.existsByName(name);
        return ResponseEntity.ok(exists);
    }

    @Operation(summary = "Создать медицинскую группу", description = "Создаёт новую медицинскую группу")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Группа успешно создана"),
        @ApiResponse(responseCode = "400", description = "Группа с таким названием уже существует")
    })
    @PostMapping("/create")
    public ResponseEntity<?> create(
            @Parameter(description = "Данные для создания медицинской группы")
            @RequestBody HealthGroupCreationDTO dto) throws EntityAlreadyExistsException {
        HealthGroupDTO healthGroup = healthGroupService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(healthGroup);
    }
}
