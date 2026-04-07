package com.sport.project.controller.rest;

import com.sport.project.dto.VisitCreationDTO;
import com.sport.project.dto.VisitDTO;
import com.sport.project.exception.EntityAlreadyExistsException;
import com.sport.project.exception.EntityNotFoundException;
import com.sport.project.service.VisitService;
import com.sport.project.service.impl.VisitServiceImpl;
import lombok.RequiredArgsConstructor;
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

@RestController
@RequestMapping("/api/visits")
@RequiredArgsConstructor
public class VisitsController {

    private final VisitServiceImpl visitService;

    @GetMapping(path = "/find-all")
    public ResponseEntity<?> findAll() {
        List<VisitDTO> visits = visitService.findAll();
        return ResponseEntity.ok(visits);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable(name = "id") Integer id) throws EntityNotFoundException {
        VisitDTO visit = visitService.findById(id);
        return ResponseEntity.ok(visit);
    }

    @GetMapping("/student/{login}")
    public ResponseEntity<?> findByStudent(@PathVariable(name = "login") String login) {
        List<VisitDTO> visits = visitService.findByStudent(login);
        return ResponseEntity.ok(visits);
    }

    @GetMapping("/lesson/{lessonId}")
    public ResponseEntity<?> findByLesson(@PathVariable(name = "lessonId") Integer lessonId) {
        List<VisitDTO> visits = visitService.findByLesson(lessonId);
        return ResponseEntity.ok(visits);
    }

    @GetMapping("/student/{login}/lesson/{lessonId}")
    public ResponseEntity<?> findByStudentAndLesson(
            @PathVariable(name = "login") String login,
            @PathVariable(name = "lessonId") Integer lessonId
    ) throws EntityNotFoundException {
        VisitDTO visit = visitService.findByStudentAndLesson(login, lessonId);
        return ResponseEntity.ok(visit);
    }

    @GetMapping("/range")
    public ResponseEntity<?> findByDateRange(
            @RequestParam(name = "from") LocalDate from,
            @RequestParam(name = "to") LocalDate to
    ) {
        List<VisitDTO> visits = visitService.findByDateRange(from, to);
        return ResponseEntity.ok(visits);
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<?> existsById(@PathVariable(name = "id") Integer id) {
        boolean exists = visitService.existsById(id);
        return ResponseEntity.ok(exists);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody VisitCreationDTO dto) throws EntityAlreadyExistsException {
        VisitDTO visit = visitService.create(dto);
        return ResponseEntity.ok(visit);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable(name = "id") Integer id) throws EntityNotFoundException {
        visitService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/student/{login}")
    public ResponseEntity<Void> deleteByStudentLogin(@PathVariable(name = "login") String login) throws EntityNotFoundException {
        visitService.deleteByStudentLogin(login);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/lesson/{lessonId}")
    public ResponseEntity<Void> deleteByLesson(@PathVariable(name = "lessonId") Integer lessonId) throws EntityNotFoundException {
        visitService.deleteByLesson(lessonId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{id}/status/{exists}")
    public ResponseEntity<Void> updateStatus(
            @PathVariable(name = "id") Integer visitId,
            @PathVariable(name = "exists") boolean isExists
    ) throws EntityNotFoundException {
        visitService.updateStatus(visitId, isExists);
        return ResponseEntity.noContent().build();
    }
}

