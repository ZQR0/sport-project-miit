package com.sport.project.controller.rest;

import com.sport.project.dto.VisitDTO;
import com.sport.project.exception.EntityNotFoundException;
import com.sport.project.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/visits")
@RequiredArgsConstructor
public class VisitsController {

    private final VisitService visitService;

    @GetMapping
    public ResponseEntity<List<VisitDTO>> findAll() {
        List<VisitDTO> visits = visitService.findAll();
        return ResponseEntity.ok(visits);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VisitDTO> findById(@PathVariable(name = "id") Integer id) throws EntityNotFoundException {
        VisitDTO visit = visitService.findById(id);
        return ResponseEntity.ok(visit);
    }

    @GetMapping("/student/{login}")
    public ResponseEntity<List<VisitDTO>> findByStudent(@PathVariable(name = "login") String login) {
        List<VisitDTO> visits = visitService.findByStudent(login);
        return ResponseEntity.ok(visits);
    }

    @GetMapping("/lesson/{lessonId}")
    public ResponseEntity<List<VisitDTO>> findByLesson(@PathVariable(name = "lessonId") Integer lessonId) {
        List<VisitDTO> visits = visitService.findByLesson(lessonId);
        return ResponseEntity.ok(visits);
    }

    @GetMapping("/student/{login}/lesson/{lessonId}")
    public ResponseEntity<VisitDTO> findByStudentAndLesson(
            @PathVariable(name = "login") String login,
            @PathVariable(name = "lessonId") Integer lessonId
    ) throws EntityNotFoundException {
        VisitDTO visit = visitService.findByStudentAndLesson(login, lessonId);
        return ResponseEntity.ok(visit);
    }

    @GetMapping("/range")
    public ResponseEntity<List<VisitDTO>> findByDateRange(
            @RequestParam(name = "from") LocalDate from,
            @RequestParam(name = "to") LocalDate to
    ) {
        List<VisitDTO> visits = visitService.findByDateRange(from, to);
        return ResponseEntity.ok(visits);
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> existsById(@PathVariable(name = "id") Integer id) {
        boolean exists = visitService.existsById(id);
        return ResponseEntity.ok(exists);
    }
}

