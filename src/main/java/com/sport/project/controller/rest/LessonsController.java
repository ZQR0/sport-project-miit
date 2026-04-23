package com.sport.project.controller.rest;

import com.sport.project.dto.LessonCreationDTO;
import com.sport.project.dto.LessonDTO;
import com.sport.project.dto.StudentDTO;
import com.sport.project.dto.VisitDTO;
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
import java.util.Map;

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

    @Operation(summary = "Получить информацию о песещаемости студента", description = "Возвращает список записей о посещениях студента для указанного студента")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Информация о посещаемости студента успешно получена"),
            @ApiResponse(responseCode = "400", description = "Некорретный ID занятия"),
            @ApiResponse(responseCode = "404", description = "Занятие не найдено")
    })
    @GetMapping("/{id}/attendance")
    public ResponseEntity<?> getAttendance(
            @Parameter(description = "ID занятия", example = "1")
            @PathVariable(name = "id") Integer id) throws EntityNotFoundException {
        List<VisitDTO> attendance = lessonService.getAttendance(id);

        return ResponseEntity.ok(attendance);
    }

    @Operation(summary = "Отметить посещаемость студента",
            description = "Отмечает присутствие или отсутствие студента на занятии")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Посещаемость успешно отмечена"),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры"),
            @ApiResponse(responseCode = "404", description = "Занятие или студент не найдены")
    })
    @PostMapping("/{id}/attendance")
    public ResponseEntity<?> markAttendance(
            @Parameter(description = "ID занятия", example = "1")
            @PathVariable(name = "id") Integer id,
            @Parameter(description = "Логин студента", example = "tkachev")
            @RequestParam(name = "studentLogin") String studentLogin,
            @Parameter(description = "Флаг присутствия", example = "true")
            @RequestParam(name = "present") boolean present)
            throws EntityNotFoundException {

        lessonService.markAttendance(id, studentLogin, present);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Получить список ожидаемых студентов",
            description = "Возвращает список студентов, ожидаемых на занятии, с информацией о посещаемости")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список ожидаемых студентов успешно получен"),
            @ApiResponse(responseCode = "400", description = "Некорректный ID занятия"),
            @ApiResponse(responseCode = "404", description = "Занятие не найдено")
    })
    @GetMapping("/{id}/expected-students")
    public ResponseEntity<?> getExpectedStudents(
            @Parameter(description = "ID занятия", example = "1")
            @PathVariable(name = "id") Integer id) throws EntityNotFoundException {

        List<StudentDTO> students = lessonService.getExpectedStudents(id);

        return ResponseEntity.ok(students);
    }

    @Operation(summary = "Получить количество отметок о посещении",
            description = "Возвращает количество записей о посещении для указанного занятия")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Количество отметок успешно получено"),
            @ApiResponse(responseCode = "400", description = "Некорректный ID занятия"),
            @ApiResponse(responseCode = "404", description = "Занятие не найдено")
    })
    @GetMapping("/{id}/attendance/count")
    public ResponseEntity<?> getAttendanceCount(
            @Parameter(description = "ID занятия", example = "1")
            @PathVariable(name = "id") Integer id) throws EntityNotFoundException {

        int count = lessonService.getAttendanceCount(id);

        return ResponseEntity.ok(count);
    }

    @Operation(summary = "Проверить возможность удаления занятия",
            description = "Возвращает true, если занятие можно удалить (нет записей о посещении)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Результат проверки успешно получен"),
            @ApiResponse(responseCode = "400", description = "Некорректный ID занятия"),
            @ApiResponse(responseCode = "404", description = "Занятие не найдено")
    })
    @GetMapping("/{id}/can-delete")
    public ResponseEntity<?> canDelete(
            @Parameter(description = "ID занятия", example = "1")
            @PathVariable(name = "id") Integer id) throws EntityNotFoundException {

        boolean canDelete = lessonService.canDelete(id);

        return ResponseEntity.ok(canDelete);
    }

    @Operation(summary = "Получить полную информацию о занятии",
            description = "Возвращает занятие с информацией о дисциплине и преподавателе")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Информация о занятии успешно получена"),
            @ApiResponse(responseCode = "400", description = "Некорректный ID занятия"),
            @ApiResponse(responseCode = "404", description = "Занятие не найдено")
    })
    @GetMapping("/{id}/details")
    public ResponseEntity<?> getWithFullDetails(
            @Parameter(description = "ID занятия", example = "1")
            @PathVariable(name = "id") Integer id) throws EntityNotFoundException {

        LessonDTO lesson = lessonService.getWithFullDetails(id);

        return ResponseEntity.ok(lesson);
    }

    @Operation(summary = "Массовая отметка посещаемости",
            description = "Отмечает посещаемость для списка студентов на занятии")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Посещаемость успешно отмечена"),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры"),
            @ApiResponse(responseCode = "404", description = "Занятие не найдено")
    })
    @PostMapping("/{id}/attendance/bulk")
    public ResponseEntity<?> bulkMarkAttendance(
            @Parameter(description = "ID занятия", example = "1")
            @PathVariable(name = "id") Integer id,
            @Parameter(description = "Мапа: логин студента - статус посещения",
                    example = "{\"ivanov\": true, \"petrov\": false}")
            @RequestBody Map<String, Boolean> attendanceMap) throws EntityNotFoundException {

        lessonService.bulkMarkAttendance(id, attendanceMap);

        return ResponseEntity.ok().build();
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

    @Operation(summary = "Удалить запись о занятии по ID", description = "Удаляет запись о занятии по её ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Запись успешно удалена"),
            @ApiResponse(responseCode = "404", description = "Запись не найдена")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(
            @Parameter(description = "ID записи о занятии", example = "1")
            @PathVariable(name = "id") Integer id) throws EntityNotFoundException {
        lessonService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Удалить запись о занятии по id дисциплины", description = "Удалить запись о занятии по id дисциплины")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Записи успешно удалены"),
            @ApiResponse(responseCode = "404", description = "Дисциплина не найдена")
    })
    @DeleteMapping("/delete/discipline/{id}")
    public ResponseEntity<Void> deleteByDiscipline(
            @Parameter(description = "ID дисциплины", example = "1")
            @PathVariable(name = "id") Integer id) throws EntityNotFoundException {
        lessonService.deleteByDiscipline(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Удалить запись о занятии по id учителя", description = "Удалить запись о занятии по id учителя")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Записи успешно удалены"),
            @ApiResponse(responseCode = "404", description = "Учитель не найден")
    })
    @DeleteMapping("/delete/teacher/{id}")
    public ResponseEntity<Void> deleteByTeacher(
            @Parameter(description = "ID учителя", example = "1")
            @PathVariable(name = "id") Integer id) throws EntityNotFoundException {
        lessonService.deleteByTeacher(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Удалить занятия по дате", description = "Удаляет все занятия, запланированные на указанную дату")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Занятия успешно удалены"),
            @ApiResponse(responseCode = "404", description = "Занятия на указанную дату не найдены")
    })
    @DeleteMapping("/delete/date/{date}")
    public ResponseEntity<Void> deleteByDate(
            @Parameter(description = "Дата занятия", example = "2024-12-25")
            @PathVariable(name = "date") LocalDate date) throws EntityNotFoundException {
        lessonService.deleteByDate(date);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Обновить дату занятия", description = "Обновляет дату занятия")
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Дата успешно обновлёна"),
            @ApiResponse(responseCode = "404", description = "Запись не найдена")
    })
    @PutMapping("/update/{id}/date/{date}")
    public ResponseEntity<Void> updateDate(
            @Parameter(description = "ID записи о занятии", example = "1")
            @PathVariable(name = "id") Integer lessonsId,
            @Parameter(description = "Дата проведения занятия", example = "2024-12-25")
            @PathVariable(name = "date") LocalDate date
    ) throws EntityNotFoundException {
        lessonService.updateDate(lessonsId, date);
        return ResponseEntity.accepted().build();
    }

    @Operation(summary = "Обновить преподавателя занятия", description = "Обновляет id преподавателя, проводящего занятие")
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Преподаватель успешно обновлен"),
            @ApiResponse(responseCode = "404", description = "Запись не найдена")
    })
    @PutMapping("/update/{id}/teacher/{teacher_id}")
    public ResponseEntity<Void> updateTeacher(
            @Parameter(description = "ID записи о занятии", example = "1")
            @PathVariable(name = "id") Integer lessonsId,
            @Parameter(description = "Id преподавателя", example = "1")
            @PathVariable(name = "teacher_id") Integer teacher_id
    ) throws EntityNotFoundException {
        lessonService.updateTeacher(lessonsId, teacher_id);
        return ResponseEntity.accepted().build();
    }

    @Operation(summary = "Обновить дисциплину занятия", description = "Обновляет id дисциплины занятия")
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Дисциплина успешно обновлена"),
            @ApiResponse(responseCode = "404", description = "Запись не найдена")
    })
    @PutMapping("/update/{id}/discipline/{discipline_id}")
    public ResponseEntity<Void> updateDiscipline(
            @Parameter(description = "ID записи о занятии", example = "1")
            @PathVariable(name = "id") Integer lessonsId,
            @Parameter(description = "Id дисциплины", example = "1")
            @PathVariable(name = "discipline_id") Integer discipline_id
            ) throws EntityNotFoundException {
        lessonService.updateDiscipline(lessonsId, discipline_id);
        return ResponseEntity.accepted().build();
    }



}
