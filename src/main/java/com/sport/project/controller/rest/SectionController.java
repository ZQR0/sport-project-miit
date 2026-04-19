package com.sport.project.controller.rest;

import com.sport.project.dto.SectionCreationDTO;
import com.sport.project.dto.SectionDTO;
import com.sport.project.dto.StudentDTO;
import com.sport.project.exception.EntityNotFoundException;
import com.sport.project.service.SectionService;
import com.sport.project.service.StudentService;
import com.sport.project.service.impl.SectionServiceImpl;
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
 * REST контроллер для управления спортивными секциями
 */
@Tag(name = "Спортивные секции", description = "API для управления спортивными секциями")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/section")
public class SectionController {

    private final SectionServiceImpl sectionService;
    private final StudentService studentService;

    @Operation(summary = "Получить все секции", description = "Возвращает список всех спортивных секций")
    @ApiResponse(responseCode = "200", description = "Секции успешно получены")
    @GetMapping("/get-all")
    public ResponseEntity<List<SectionDTO>> getAll() {
        return ResponseEntity.ok(sectionService.findAll());
    }

    @Operation(summary = "Получить секцию по ID", description = "Возвращает спортивную секцию по её уникальному идентификатору")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Секция найдена"),
        @ApiResponse(responseCode = "404", description = "Секция не найдена")
    })
    @GetMapping("/{id}")
    public ResponseEntity<SectionDTO> getSectionById(
            @Parameter(description = "ID секции", example = "1")
            @PathVariable("id") Integer id) {
        return ResponseEntity.ok(sectionService.findById(id));
    }

    @Operation(summary = "Получить секцию по названию", description = "Возвращает спортивную секцию по её названию")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Секция найдена"),
        @ApiResponse(responseCode = "404", description = "Секция не найдена")
    })
    @GetMapping("/by-name")
    public ResponseEntity<SectionDTO> getSectionByName(
            @Parameter(description = "Название секции", example = "Футбол")
            @RequestParam String name) {
        return ResponseEntity.ok(sectionService.findByName(name));
    }

    @Operation(summary = "Получить студентов секции по ID", description = "Возвращает список студентов спортивной секции по её ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Студенты найдены"),
        @ApiResponse(responseCode = "404", description = "Секция не найдена")
    })
    @GetMapping("/section-student-by-section-id/{sectionId}/students")
    public ResponseEntity<List<StudentDTO>> getStudentsBySectionId(
            @Parameter(description = "ID секции", example = "1")
            @PathVariable(name = "sectionId") Integer sectionId) {
        return ResponseEntity.ok(sectionService.getStudents(sectionId));
    }

    @Operation(summary = "Получить студентов секции по названию", description = "Возвращает список студентов спортивной секции по её названию")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Студенты найдены"),
        @ApiResponse(responseCode = "404", description = "Секция не найдена")
    })
    @GetMapping("/section-student-by-section-name/{sectionName}/students")
    public ResponseEntity<List<StudentDTO>> getStudentsBySectionName(
            @Parameter(description = "Название секции", example = "Футбол")
            @PathVariable(name = "sectionName") String sectionName) {
        return ResponseEntity.ok(sectionService.getStudents(sectionName));
    }

    @Operation(summary = "Создать спортивную секцию", description = "Создаёт новую спортивную секцию")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Секция успешно создана"),
        @ApiResponse(responseCode = "400", description = "Секция с таким названием уже существует")
    })
    @PostMapping("/create")
    public ResponseEntity<?> create(
            @Parameter(description = "Данные для создания секции")
            @RequestBody SectionCreationDTO dto) {
        SectionDTO section = sectionService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(section);
    }


    @Operation(summary = "Удалить секцию по ID", description = "Удаляет запись секции по её ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Запись успешно удалена"),
            @ApiResponse(responseCode = "404", description = "Запись не найдена")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById (
            @Parameter(description = "ID записи секции", example = "1")
            @PathVariable(name = "id") Integer id) throws EntityNotFoundException {
        sectionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Удалить секцию по названию", description = "Удаляет запись секции по её названию")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Запись успешно удалена"),
            @ApiResponse(responseCode = "404", description = "Запись не найдена")
    })
    @DeleteMapping("/delete/by-name")
    public ResponseEntity<Void> deleteByName (
            @Parameter(description = "Название записи секции", example = "Футбол")
            @RequestParam(name = "name") String name) throws EntityNotFoundException {
        sectionService.deleteByName(name);
        return ResponseEntity.noContent().build();
    }


//  Не знаю, надо ли писать этот метод в контроллере
    @Operation(summary = "Обновить секцию", description = "Обновляет название секции")
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Название секции успешно обнавлено"),
            @ApiResponse(responseCode = "404", description = "Запись не найдена")
    })
    @PutMapping("/update/{id}/name/{name}")
    public ResponseEntity<Void> updateName(
            @Parameter(description = "ID секции для обновления", example = "1")
            @PathVariable(name = "id") Integer id,
            @Parameter(description = "Новое название секции", example = "Гимнастика"),
            @PathVariable(name = "name") String name
    )throws EntityNotFoundException {
        sectionService.updateName(id, name);
        return ResponseEntity.accepted().build();
    }

    @Operation(summary = "Обновить секцию", description = "Обновляет название секции")
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Название секции успешно обнавлено"),
            @ApiResponse(responseCode = "404", description = "Запись не найдена")
    })
    @PutMapping("/update/{id}/description/{description}")
    public ResponseEntity<Void> updateDescription(
            @Parameter(description = "ID секции для обновления", example = "1")
            @PathVariable(name = "id") Integer id,
            @Parameter(description = "Новое описание секции", example = "Спортивная"),
            @PathVariable(name = "description") String description
    )throws EntityNotFoundException {
        sectionService.updateDescription(id, description);
        return ResponseEntity.accepted().build();
    }
}
