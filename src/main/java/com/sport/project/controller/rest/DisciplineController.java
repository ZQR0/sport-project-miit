package com.sport.project.controller.rest;

import com.sport.project.dto.DisciplineDTO;
import com.sport.project.dto.LessonDTO;
import com.sport.project.service.DisciplineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/discipline")
public class DisciplineController {
    private final DisciplineService disciplineService;

    //Вывод всех дисциплин
    @GetMapping
    public ResponseEntity<List<DisciplineDTO>> getAll() {
        return ResponseEntity.ok(disciplineService.findAll());
    }

    //Поиск дисциплины по айди
    @GetMapping("/{id}")
    public ResponseEntity<DisciplineDTO> getDiscipline(@PathVariable Integer id) {
        return ResponseEntity.ok(disciplineService.findById(id));
    }

    //Поиск по имени дисциплины
    @GetMapping("/by-name")
    public ResponseEntity<DisciplineDTO> getDisciplineByName(@RequestParam String name) {
        return ResponseEntity.ok(disciplineService.findByName(name));
    }

    @GetMapping("/{disciplineId}/lessons")
    public ResponseEntity<List<LessonDTO>> getLessonsByDisciplineId(@PathVariable Integer disciplineId) {
        return ResponseEntity.ok(disciplineService.getLessons(disciplineId));
    }

    @GetMapping("/by-name/lessons")
    public ResponseEntity<List<LessonDTO>> getLessonsByDisciplineName(@RequestParam String disciplineName) {
        return ResponseEntity.ok(disciplineService.getLessons(disciplineName));
    }
}
