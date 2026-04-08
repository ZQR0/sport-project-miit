package com.sport.project.controller.rest;

import com.sport.project.dto.SectionDTO;
import com.sport.project.dto.StudentDTO;
import com.sport.project.service.SectionService;
import com.sport.project.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/service")
public class SectionController {

    private final SectionService sectionService;
    private final StudentService studentService;

    //Получить все секции
    @GetMapping("/get-all")
    public ResponseEntity<List<SectionDTO>> getAll() {
        return ResponseEntity.ok(sectionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SectionDTO> getSectionById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(sectionService.findById(id));
    }

    @GetMapping("/by-name")
    public ResponseEntity<SectionDTO> getSectionByName(@RequestParam String name) {
        return ResponseEntity.ok(sectionService.findByName(name));
    }

    @GetMapping("/{sectionId}/students")
    public ResponseEntity<List<StudentDTO>> getStudentsBySectionId (@PathVariable(name = "sectionId") Integer sectionId) {
        return ResponseEntity.ok(sectionService.getStudents(sectionId));
    }

    @GetMapping("/{sectionName}/students")
    public ResponseEntity<List<StudentDTO>> getStudentsBySectionName (@PathVariable(name = "sectionName") String sectionName) {
        return ResponseEntity.ok(sectionService.getStudents(sectionName));
    }
}
