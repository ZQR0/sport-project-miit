package com.sport.project.controller.rest;

import com.sport.project.dto.AttendanceInfo;
import com.sport.project.dto.StudentDTO;
import com.sport.project.dto.VisitCreationDTO;
import com.sport.project.dto.VisitDTO;
import com.sport.project.exception.EntityAlreadyExistsException;
import com.sport.project.exception.EntityNotFoundException;
import com.sport.project.service.VisitService;
import com.sport.project.service.impl.VisitServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * REST контроллер для управления посещаемостью
 */
@Tag(name = "Посещаемость", description = "API для управления посещаемостью занятий")
@RestController
@RequestMapping("/api/visits")
@RequiredArgsConstructor
public class VisitsController {

    private final VisitServiceImpl visitService;

    @Operation(summary = "Получить все записи о посещениях", description = "Возвращает список всех записей о посещениях")
    @ApiResponse(responseCode = "200", description = "Записи успешно получены")
    @GetMapping(path = "/find-all")
    public ResponseEntity<?> findAll() {
        List<VisitDTO> visits = visitService.findAll();
        return ResponseEntity.ok(visits);
    }

    @Operation(summary = "Получить запись о посещении по ID", description = "Возвращает запись о посещении по её уникальному идентификатору")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Запись найдена"),
        @ApiResponse(responseCode = "404", description = "Запись не найдена")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(
            @Parameter(description = "ID записи о посещении", example = "1")
            @PathVariable(name = "id") Integer id) throws EntityNotFoundException {
        VisitDTO visit = visitService.findById(id);
        return ResponseEntity.ok(visit);
    }

    @Operation(summary = "Получить посещения студента", description = "Возвращает список всех посещений студента по его логину")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Посещения найдены"),
        @ApiResponse(responseCode = "404", description = "Студент не найден")
    })
    @GetMapping("/student/{login}")
    public ResponseEntity<?> findByStudent(
            @Parameter(description = "Логин студента", example = "ivanov")
            @PathVariable(name = "login") String login) throws EntityNotFoundException {
        List<VisitDTO> visits = visitService.findByStudent(login);
        return ResponseEntity.status(HttpStatus.OK).body(visits);
    }

    @Operation(summary = "Получить посещения занятия", description = "Возвращает список посещений занятия по его ID")
    @ApiResponse(responseCode = "200", description = "Посещения найдены")
    @GetMapping("/lesson/{lessonId}")
    public ResponseEntity<?> findByLesson(
            @Parameter(description = "ID занятия", example = "10")
            @PathVariable(name = "lessonId") Integer lessonId) {
        List<VisitDTO> visits = visitService.findByLesson(lessonId);
        return ResponseEntity.ok(visits);
    }

    @Operation(summary = "Получить посещение студента на занятии", description = "Возвращает запись о посещении студента на конкретном занятии")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Запись найдена"),
        @ApiResponse(responseCode = "404", description = "Запись не найдена")
    })
    @GetMapping("/student/{login}/lesson/{lessonId}")
    public ResponseEntity<?> findByStudentAndLesson(
            @Parameter(description = "Логин студента", example = "ivanov")
            @PathVariable(name = "login") String login,
            @Parameter(description = "ID занятия", example = "10")
            @PathVariable(name = "lessonId") Integer lessonId
    ) throws EntityNotFoundException {
        VisitDTO visit = visitService.findByStudentAndLesson(login, lessonId);
        return ResponseEntity.ok(visit);
    }

    @Operation(summary = "Получить посещения по диапазону дат", description = "Возвращает список посещений за указанный период")
    @ApiResponse(responseCode = "200", description = "Посещения найдены")
    @GetMapping("/range")
    public ResponseEntity<?> findByDateRange(
            @Parameter(description = "Начальная дата", example = "2024-09-01")
            @RequestParam(name = "from") LocalDate from,
            @Parameter(description = "Конечная дата", example = "2024-09-30")
            @RequestParam(name = "to") LocalDate to
    ) {
        List<VisitDTO> visits = visitService.findByDateRange(from, to);
        return ResponseEntity.ok(visits);
    }

    @Operation(summary = "Проверить существование записи по ID", description = "Проверяет существование записи о посещении по ID")
    @ApiResponse(responseCode = "200", description = "Результат проверки")
    @GetMapping("/exists/{id}")
    public ResponseEntity<?> existsById(
            @Parameter(description = "ID записи о посещении", example = "1")
            @PathVariable(name = "id") Integer id) {
        boolean exists = visitService.existsById(id);
        return ResponseEntity.ok(exists);
    }

    @Operation(summary = "Получить общее количество пропусков студента", description = "Возвращает общее количество пропусков студента")
    @ApiResponse(responseCode = "200", description = "Количество пропусков успешно получено")
    @GetMapping("/get-total-absences/{studentLogin}")
    public Map<String, Integer> getTotalAbsences(
            @Parameter(description = "Логин студента", example = "ivanov")
            @PathVariable(name = "studentLogin") String studentLogin) {
        int count = this.visitService.getTotalAbsences(studentLogin);
        return Map.of("totalCount", count);
    }

    @Operation(summary = "Получение отсутствующих студентов на паре", description = "Возвращает список студентов, которых нет на паре")
    @GetMapping("/students/absent/{lessonId}")
    public ResponseEntity<?> getAbsentStudentsForLesson(@PathVariable(name = "lessonId") Integer lessonId) throws EntityNotFoundException {
        List<StudentDTO> absentStudents = this.visitService.getAbsentStudentsForLesson(lessonId);
        return new ResponseEntity<>(
                absentStudents,
                HttpStatus.OK
        );
    }

    @GetMapping(path = "/attendanceMap/{studentLogin}")
    public ResponseEntity<?> getAttendanceMap(@PathVariable(name = "studentLogin") String studentLogin) throws EntityNotFoundException {
        Map<LocalDate, List<AttendanceInfo>> attendanceMap = this.visitService.getStudentAttendanceMap(studentLogin);

        return new ResponseEntity<>(
                attendanceMap,
                HttpStatus.OK
        );
    }

    @Operation(summary = "Создать запись о посещении", description = "Создаёт новую запись о посещении занятия")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Запись успешно создана"),
        @ApiResponse(responseCode = "400", description = "Запись уже существует или ошибка данных")
    })
    @PostMapping("/create")
    public ResponseEntity<?> create(
            @Parameter(description = "Данные для создания записи о посещении")
            @RequestBody VisitCreationDTO dto) throws EntityAlreadyExistsException {
        VisitDTO visit = visitService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(visit);
    }

    @Operation(summary = "Удалить запись о посещении по ID", description = "Удаляет запись о посещении по её ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Запись успешно удалена"),
        @ApiResponse(responseCode = "404", description = "Запись не найдена")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(
            @Parameter(description = "ID записи о посещении", example = "1")
            @PathVariable(name = "id") Integer id) throws EntityNotFoundException {
        visitService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Удалить все посещения студента", description = "Удаляет все записи о посещениях студента по его логину")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Посещения успешно удалены"),
        @ApiResponse(responseCode = "404", description = "Студент не найден")
    })
    @DeleteMapping("/delete/student/{login}")
    public ResponseEntity<Void> deleteByStudentLogin(
            @Parameter(description = "Логин студента", example = "ivanov")
            @PathVariable(name = "login") String login) throws EntityNotFoundException {
        visitService.deleteByStudentLogin(login);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Удалить все посещения занятия", description = "Удаляет все записи о посещениях занятия по его ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Посещения успешно удалены"),
        @ApiResponse(responseCode = "404", description = "Занятие не найдено")
    })
    @DeleteMapping("/delete/lesson/{lessonId}")
    public ResponseEntity<Void> deleteByLesson(
            @Parameter(description = "ID занятия", example = "10")
            @PathVariable(name = "lessonId") Integer lessonId) throws EntityNotFoundException {
        visitService.deleteByLesson(lessonId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Обновить статус посещения", description = "Обновляет статус присутствия студента на занятии")
    @ApiResponses({
        @ApiResponse(responseCode = "202", description = "Статус успешно обновлён"),
        @ApiResponse(responseCode = "404", description = "Запись не найдена")
    })
    @PutMapping("/update/{id}/status/{exists}")
    public ResponseEntity<Void> updateStatus(
            @Parameter(description = "ID записи о посещении", example = "1")
            @PathVariable(name = "id") Integer visitId,
            @Parameter(description = "Статус присутствия (true - был, false - отсутствовал)", example = "true")
            @PathVariable(name = "exists") boolean isExists
    ) throws EntityNotFoundException {
        visitService.updateStatus(visitId, isExists);
        return ResponseEntity.accepted().build();
    }
}

