package com.sport.project.controller.rest;

import com.sport.project.dto.SectionCreationDTO;
import com.sport.project.dto.SectionDTO;
import com.sport.project.dto.StudentDTO;
import com.sport.project.service.SectionService;
import com.sport.project.service.StudentService;
import com.sport.project.service.impl.SectionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/section")
public class SectionController {

    private final SectionServiceImpl sectionService;
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

    @GetMapping("/section-student-by-section-id/{sectionId}/students")
    public ResponseEntity<List<StudentDTO>> getStudentsBySectionId (@PathVariable(name = "sectionId") Integer sectionId) {
        return ResponseEntity.ok(sectionService.getStudents(sectionId));
    }

    @GetMapping("/section-student-by-section-name/{sectionName}/students")
    public ResponseEntity<List<StudentDTO>> getStudentsBySectionName (@PathVariable(name = "sectionName") String sectionName) {
        return ResponseEntity.ok(sectionService.getStudents(sectionName));
    }

    @PostMapping("/create")
    public ResponseEntity<?> create (@RequestBody SectionCreationDTO dto) {
        SectionDTO section = sectionService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(section);
    }
//
//    @PostMapping("/create")
//    public ResponseEntity<?> create (@RequestParam String name, @RequestParam String descruption) {
//        SectionDTO section = sectionService.create(name, descruption);
//        return ResponseEntity.status(HttpStatus.CREATED).body(section);
//    }
}
