package com.sport.project.controller.rest;

import com.sport.project.dto.TeacherCreationDTO;
import com.sport.project.dto.TeacherDTO;
import com.sport.project.service.impl.TeacherServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * REST контроллер для управления преподавателями
 */
@Tag(name = "Преподаватели", description = "API для управления преподавателями")
@RestController
@RequestMapping(path = "/teachers")
@RequiredArgsConstructor
public class RestTeacherController {

    private final TeacherServiceImpl teacherService;

    @Operation(summary = "Получить преподавателя по логину", description = "Возвращает преподавателя по его логину")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Преподаватель найден"),
        @ApiResponse(responseCode = "404", description = "Преподаватель не найден")
    })
    @GetMapping(path = "/find-by-login", params = "login")
    public TeacherDTO findByLogin(
            @Parameter(description = "Логин преподавателя", example = "petrov")
            @RequestParam(name = "login") String login) {
        return this.teacherService.findByLogin(login);
    }

    @Operation(summary = "Получить преподавателя по ФИО", description = "Возвращает список преподавателей по ФИО")
    @ApiResponse(responseCode = "200", description = "Преподаватели найдены")
    @GetMapping(path = "/find-by-full-name")
    public List<TeacherDTO> findByFullName(
            @Parameter(description = "Имя преподавателя", example = "Пётр")
            @RequestParam(name = "first-name") String firstName,
            @Parameter(description = "Фамилия преподавателя", example = "Петров")
            @RequestParam(name = "last-name") String lastName,
            @Parameter(description = "Отчество преподавателя", example = "Петрович")
            @RequestParam(name = "patronymic", required = false) String patronymic) {
        return this.teacherService.findByFullName(firstName, lastName, patronymic);
    }

    @Operation(summary = "Получить всех преподавателей", description = "Возвращает список всех преподавателей")
    @ApiResponse(responseCode = "200", description = "Преподаватели успешно получены")
    @GetMapping(path = "/find-all")
    public List<TeacherDTO> findAll() {
        return this.teacherService.findAll();
    }

    @Operation(summary = "Получить всех модераторов", description = "Возвращает список преподавателей с правами модератора")
    @ApiResponse(responseCode = "200", description = "Модераторы успешно получены")
    @GetMapping(path = "/find-all-moderators")
    public List<TeacherDTO> findAllModerators() {
        return this.teacherService.findAllModerators();
    }

    @Operation(summary = "Получить преподавателей по дате занятия", description = "Возвращает список преподавателей, у которых есть занятия в указанную дату")
    @ApiResponse(responseCode = "200", description = "Преподаватели найдены")
    @GetMapping(path = "/get-by-lesson-date")
    public List<TeacherDTO> findByLessonDate(
            @Parameter(description = "Дата занятия", example = "2024-09-01")
            @RequestParam(name = "date") LocalDate date) {
        return this.teacherService.findByLessonsDate(date);
    }

    @Operation(summary = "Проверить существование преподавателя по логину", description = "Проверяет существование преподавателя по логину")
    @ApiResponse(responseCode = "200", description = "Результат проверки")
    @GetMapping(path = "/is-exists-by-login")
    public boolean isExistByLogin(
            @Parameter(description = "Логин преподавателя", example = "petrov")
            @RequestParam(name = "login") String login) {
        return this.teacherService.existsByLogin(login);
    }

    @Operation(summary = "Создать преподавателя", description = "Создаёт нового преподавателя")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Преподаватель успешно создан"),
        @ApiResponse(responseCode = "400", description = "Преподаватель с таким логином уже существует")
    })
    @PostMapping(path = "/create")
    public ResponseEntity<TeacherDTO> create(
            @Parameter(description = "Данные для создания преподавателя")
            @RequestBody TeacherCreationDTO dto) {
        TeacherDTO created = teacherService.create(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

}
