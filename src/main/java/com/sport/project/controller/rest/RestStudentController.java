package com.sport.project.controller.rest;

import com.sport.project.dto.StudentCreationDTO;
import com.sport.project.dto.StudentDTO;
import com.sport.project.service.impl.StudentServiceImpl;
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
 * REST контроллер для управления студентами
 */
@Tag(name = "Студенты", description = "API для управления студентами")
@RestController
@RequestMapping(path = "/students")
@RequiredArgsConstructor
public class RestStudentController {

    private final StudentServiceImpl studentService;

    @Operation(summary = "Получить студента по логину", description = "Возвращает студента по его логину")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Студент найден"),
        @ApiResponse(responseCode = "404", description = "Студент не найден")
    })
    @GetMapping(path = "/find-by-login", params = "login")
    public StudentDTO findByLogin(
            @Parameter(description = "Логин студента", example = "ivanov")
            @RequestParam(name = "login") String login) {
        return this.studentService.findByLogin(login);
    }

    @Operation(summary = "Получить студента по ФИО", description = "Возвращает список студентов по ФИО")
    @ApiResponse(responseCode = "200", description = "Студенты найдены")
    @GetMapping(path = "/find-by-full-name")
    public List<StudentDTO> findByFullName(
            @Parameter(description = "Имя студента", example = "Иван")
            @RequestParam(name = "first-name") String firstName,
            @Parameter(description = "Фамилия студента", example = "Иванов")
            @RequestParam(name = "last-name") String lastName,
            @Parameter(description = "Отчество студента", example = "Иванович")
            @RequestParam(name = "patronymic", required = false) String patronymic) {
        return this.studentService.findByFullName(firstName, lastName, patronymic);
    }

    @Operation(summary = "Получить всех студентов", description = "Возвращает список всех студентов")
    @ApiResponse(responseCode = "200", description = "Студенты успешно получены")
    @GetMapping(path = "/find-all")
    public List<StudentDTO> findAll() {
        return this.studentService.findAll();
    }

    @Operation(summary = "Получить студентов по группе", description = "Возвращает список студентов учебной группы по её ID")
    @ApiResponse(responseCode = "200", description = "Студенты найдены")
    @GetMapping(path = "/find-by-group")
    public List<StudentDTO> findByGroup(
            @Parameter(description = "ID учебной группы", example = "1")
            @RequestParam(name = "group-id") Integer groupId) {
        return this.studentService.findByGroup(groupId);
    }

    @Operation(summary = "Получить студентов по медицинской группе", description = "Возвращает список студентов медицинской группы по её ID")
    @ApiResponse(responseCode = "200", description = "Студенты найдены")
    @GetMapping(path = "/find-by-health-group")
    public List<StudentDTO> findByHealthGroup(
            @Parameter(description = "ID медицинской группы", example = "2")
            @RequestParam(name = "health-group-id") Integer healthGroupId) {
        return this.studentService.findByHealthGroup(healthGroupId);
    }

    @Operation(summary = "Получить студентов по секции", description = "Возвращает список студентов спортивной секции по её ID")
    @ApiResponse(responseCode = "200", description = "Студенты найдены")
    @GetMapping(path = "/find-by-section")
    public List<StudentDTO> findBySection(
            @Parameter(description = "ID секции", example = "1")
            @RequestParam(name = "section-id") Integer sectionId) {
        return this.studentService.findBySection(sectionId);
    }

    @Operation(summary = "Проверить существование студента по логину", description = "Проверяет существование студента по логину")
    @ApiResponse(responseCode = "200", description = "Результат проверки")
    @GetMapping(path = "/is-exists-by-login")
    public boolean isExistByLogin(
            @Parameter(description = "Логин студента", example = "ivanov")
            @RequestParam(name = "login") String login) {
        return this.studentService.existsByLogin(login);
    }

    @Operation(summary = "Создать студента", description = "Создаёт нового студента")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Студент успешно создан"),
        @ApiResponse(responseCode = "400", description = "Студент с таким логином уже существует")
    })
    @PostMapping(path = "/create")
    public ResponseEntity<StudentDTO> create(
            @Parameter(description = "Данные для создания студента")
            @RequestBody StudentCreationDTO dto) {
        StudentDTO created = studentService.create(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
