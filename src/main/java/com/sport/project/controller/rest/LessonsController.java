package com.sport.project.controller.rest;

import com.sport.project.dto.LessonCreationDTO;
import com.sport.project.dto.LessonDTO;
import com.sport.project.exception.EntityAlreadyExistsException;
import com.sport.project.service.impl.LessonServiceImpl;
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

import java.time.LocalDate;
import java.util.List;

/**
 * REST контроллер для управления занятиями
 */
@Tag(name = "Занятия", description = "API для управления занятиями")
@RestController
@RequestMapping("/api/lessons")
@RequiredArgsConstructor
public class LessonsController {

    private final LessonServiceImpl lessonService;

    @Operation(summary = "Получить занятие по ID", description = "Возвращает занятие по его уникальному идентификатору")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Занятие найдено"),
        @ApiResponse(responseCode = "404", description = "Занятие не найдено")
    })
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> findByID(
            @Parameter(description = "ID занятия", example = "1")
            @PathVariable(name = "id") Integer id) throws EntityNotFoundException {
        LessonDTO lesson = lessonService.findById(id);
        return ResponseEntity.ok(lesson);

    }

    @Operation(summary = "Получить занятие по дисциплине и дате", description = "Возвращает занятие по ID дисциплины и дате проведения")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Занятие найдено"),
        @ApiResponse(responseCode = "404", description = "Занятие не найдено")
    })
    @GetMapping(path = "/discipline-and-date")
    public ResponseEntity<?> findByDisciplineAndDate(
            @Parameter(description = "ID дисциплины", example = "1")
            @RequestParam(name = "disciplineId") Integer disciplineId,
            @Parameter(description = "Дата занятия", example = "2024-09-01")
            @RequestParam(name = "date") LocalDate date
    ) throws EntityNotFoundException {
        LessonDTO lesson = lessonService.findByDisciplineAndDate(disciplineId, date);
        return ResponseEntity.ok(lesson);
    }

    @Operation(summary = "Получить все занятия", description = "Возвращает список всех занятий")
    @ApiResponse(responseCode = "200", description = "Занятия успешно получены")
    @GetMapping(path = "/find-all")
    public ResponseEntity<?> findAll() {
        List<LessonDTO> lessons = lessonService.findAll();
        return ResponseEntity.ok(lessons);
    }

    @Operation(summary = "Получить занятия по дате", description = "Возвращает список занятий за указанную дату")
    @ApiResponse(responseCode = "200", description = "Занятия найдены")
    @GetMapping(path = "/date")
    public ResponseEntity<?> findByDate(
            @Parameter(description = "Дата занятия", example = "2024-09-01")
            @RequestParam(name = "date") LocalDate date) {
        List<LessonDTO> lessons = lessonService.findByDate(date);
        return ResponseEntity.ok(lessons);
    }

    @Operation(summary = "Получить занятия преподавателя", description = "Возвращает список занятий преподавателя по его ID")
    @ApiResponse(responseCode = "200", description = "Занятия найдены")
    @GetMapping(path = "/teacher/{teacherId}")
    public ResponseEntity<?> findByTeacher(
            @Parameter(description = "ID преподавателя", example = "3")
            @PathVariable(name = "teacherId") Integer teacherId) {
        List<LessonDTO> lessons = lessonService.findByTeacher(teacherId);
        return ResponseEntity.ok(lessons);
    }

    @Operation(summary = "Получить занятия по дисциплине", description = "Возвращает список занятий учебной дисциплины по её ID")
    @ApiResponse(responseCode = "200", description = "Занятия найдены")
    @GetMapping(path = "/discipline/{disciplineId}")
    public ResponseEntity<?> findByDiscipline(
            @Parameter(description = "ID дисциплины", example = "1")
            @PathVariable(name = "disciplineId") Integer disciplineId) {
        List<LessonDTO> lessons = lessonService.findByDiscipline(disciplineId);
        return ResponseEntity.ok(lessons);
    }

    @Operation(summary = "Получить занятия по названию дисциплины", description = "Возвращает список занятий по названию дисциплины")
    @ApiResponse(responseCode = "200", description = "Занятия найдены")
    @GetMapping(path = "/discipline-name/{name}")
    public ResponseEntity<?> findByDisciplineName(
            @Parameter(description = "Название дисциплины", example = "Физическая культура")
            @PathVariable(name = "name") String name) {
        List<LessonDTO> lessons = lessonService.findByDisciplineName(name);
        return ResponseEntity.ok(lessons);
    }

    @Operation(summary = "Получить занятия по диапазону дат", description = "Возвращает список занятий за период")
    @ApiResponse(responseCode = "200", description = "Занятия найдены")
    @GetMapping(path = "/range")
    public ResponseEntity<?> findByDateRange(
            @Parameter(description = "Начальная дата", example = "2024-09-01")
            @RequestParam(name = "from") LocalDate from,
            @Parameter(description = "Конечная дата", example = "2024-09-30")
            @RequestParam(name = "to") LocalDate to
    ) {
        List<LessonDTO> lessons = lessonService.findByDateRange(from, to);
        return ResponseEntity.ok(lessons);
    }

    @Operation(summary = "Проверить существование занятия по ID", description = "Проверяет существование занятия по ID")
    @ApiResponse(responseCode = "200", description = "Результат проверки")
    @GetMapping(path = "/exists/{id}")
    public ResponseEntity<?> existsById(
            @Parameter(description = "ID занятия", example = "1")
            @PathVariable(name = "id") Integer id) {
        boolean exists = lessonService.existsById(id);
        return ResponseEntity.ok(exists);
    }

    @Operation(summary = "Создать занятие", description = "Создаёт новое занятие")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Занятие успешно создано"),
        @ApiResponse(responseCode = "400", description = "Ошибка при создании занятия")
    })
    @PostMapping(path = "/create")
    public ResponseEntity<?> create(
            @Parameter(description = "Данные для создания занятия")
            @RequestBody LessonCreationDTO dto) throws EntityAlreadyExistsException {
        LessonDTO lesson = lessonService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(lesson);
    }

}
