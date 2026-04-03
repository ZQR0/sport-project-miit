package com.sport.project.controller.rest;

import com.sport.project.dto.DisciplineDTO;
import com.sport.project.service.DisciplineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/discipline")
public class DisciplineController {
    private final DisciplineService disciplineService;

    //Поиск дисциплины по айди
    @GetMapping("/{id}")
    public ResponseEntity<DisciplineDTO> getDiscipline(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(disciplineService.findById(id));
    }
}
