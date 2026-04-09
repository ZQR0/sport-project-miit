package com.sport.project.controller.rest;

import com.sport.project.dto.*;
import com.sport.project.exception.EntityNotFoundException;
import com.sport.project.service.DisciplineService;
import com.sport.project.service.impl.DisciplineServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/discipline")
public class DisciplineController {
    private final DisciplineServiceImpl disciplineService;

    //Вывод всех дисциплин
    @GetMapping(path = "/find-all")
    public ResponseEntity<List<DisciplineDTO>> findAll() {
        return ResponseEntity.ok(disciplineService.findAll());
    }

    //Поиск дисциплины по айди
    @GetMapping("/{id}")
    public ResponseEntity<DisciplineDTO> findById(
            @PathVariable(name = "id") Integer id) throws com.sport.project.exception.EntityNotFoundException {
        return ResponseEntity.ok(disciplineService.findById(id));
    }

    //Поиск по имени дисциплины
    @GetMapping("/by-name")
    public ResponseEntity<DisciplineDTO> findByName(
            @RequestParam(name = "name") String name) throws com.sport.project.exception.EntityNotFoundException {
        return ResponseEntity.ok(disciplineService.findByName(name));
    }


    @GetMapping("/by-discipline-id/{disciplineId}/lessons")
    public ResponseEntity<List<LessonDTO>> getLessonsByDisciplineId(
            @PathVariable(name = "disciplineId") Integer disciplineId) throws com.sport.project.exception.EntityNotFoundException {
        return ResponseEntity.ok(disciplineService.getLessons(disciplineId));
    }

    //FIXME: перенести ввод названия группы из URL в Request Body
    @GetMapping("/by-discipline-name/{disciplineName}/lessons")
    public ResponseEntity<List<LessonDTO>> getLessons(
            @PathVariable(name = "disciplineName") String disciplineName) throws EntityNotFoundException {
        return ResponseEntity.ok(disciplineService.getLessons(disciplineName));
    }

    // Проверка существования дисциплины по названию
    @GetMapping(path = "/is-exists-by-name")
    public boolean existsByName(@RequestParam(name = "disciplineName") String disciplineName) {
        return this.disciplineService.existsByName(disciplineName);
    }

    // Создание дисциплины через DTO
    @PostMapping(path = "/create")
    public ResponseEntity<DisciplineDTO> create(@RequestBody DisciplineCreationDTO dto) {
        DisciplineDTO created = disciplineService.create(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Создание дисциплины через имя (в path не уверена)
    @PostMapping(path = "/create-by-name")
    public ResponseEntity<DisciplineDTO> create(@RequestBody String name) {
        DisciplineDTO created = disciplineService.create(name);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
