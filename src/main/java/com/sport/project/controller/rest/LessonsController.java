package com.sport.project.controller.rest;

import com.sport.project.dto.LessonCreationDTO;
import com.sport.project.dto.LessonDTO;
import com.sport.project.exception.EntityAlreadyExistsException;
import com.sport.project.service.impl.LessonServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/lessons")
@RequiredArgsConstructor
public class LessonsController {

    private final LessonServiceImpl lessonService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> findByID(@PathVariable(name = "id") Integer id) throws EntityNotFoundException {
        LessonDTO lesson = lessonService.findById(id);
        return ResponseEntity.ok(lesson);

    }

    @GetMapping(path = "/discipline-and-date")
    public ResponseEntity<?> findByDisciplineAndDate(
            @RequestParam(name = "disciplineId") Integer disciplineId,
            @RequestParam(name = "date") LocalDate date
    ) throws EntityNotFoundException {
        LessonDTO lesson = lessonService.findByDisciplineAndDate(disciplineId, date);
        return ResponseEntity.ok(lesson);
    }

    @GetMapping(path = "/find-all")
    public ResponseEntity<?> findAll() {
        List<LessonDTO> lessons = lessonService.findAll();
        return ResponseEntity.ok(lessons);
    }

    @GetMapping(path = "/date")
    public ResponseEntity<?> findByDate(@RequestParam(name = "date") LocalDate date) {
        List<LessonDTO> lessons = lessonService.findByDate(date);
        return ResponseEntity.ok(lessons);
    }

    @GetMapping(path = "/teacher/{teacherId}")
    public ResponseEntity<?> findByTeacher(@PathVariable(name = "teacherId") Integer teacherId) {
        List<LessonDTO> lessons = lessonService.findByTeacher(teacherId);
        return ResponseEntity.ok(lessons);
    }

    @GetMapping(path = "/discipline/{disciplineId}")
    public ResponseEntity<?> findByDiscipline(@PathVariable(name = "disciplineId") Integer disciplineId) {
        List<LessonDTO> lessons = lessonService.findByDiscipline(disciplineId);
        return ResponseEntity.ok(lessons);
    }

    @GetMapping(path = "/discipline-name/{name}")
    public ResponseEntity<?> findByDisciplineName(@PathVariable(name = "name") String name) {
        List<LessonDTO> lessons = lessonService.findByDisciplineName(name);
        return ResponseEntity.ok(lessons);
    }

    @GetMapping(path = "/range")
    public ResponseEntity<?> findByDateRange(
            @RequestParam(name = "from") LocalDate from,
            @RequestParam(name = "to") LocalDate to
    ) {
        List<LessonDTO> lessons = lessonService.findByDateRange(from, to);
        return ResponseEntity.ok(lessons);
    }

    @GetMapping(path = "/exists/{id}")
    public ResponseEntity<?> existsById(@PathVariable(name = "id") Integer id) {
        boolean exists = lessonService.existsById(id);
        return ResponseEntity.ok(exists);
    }

    @PostMapping(path = "/create")
    public ResponseEntity<?> create(@RequestBody LessonCreationDTO dto) throws EntityAlreadyExistsException {
        LessonDTO lesson = lessonService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(lesson);
    }

}
