package com.sport.project.controller.rest;

import com.sport.project.dto.StudentCreationDTO;
import com.sport.project.dto.StudentDTO;
import com.sport.project.service.impl.StudentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/students")
@RequiredArgsConstructor
public class RestStudentController {

    private final StudentServiceImpl studentService;

    @GetMapping(path = "/find-by-login", params = "login")
    public StudentDTO findByLogin(@RequestParam(name = "login") String login) {
        return this.studentService.findByLogin(login);
    }

    @GetMapping(path = "/find-by-full-name")
    public List<StudentDTO> findByFullName(
            @RequestParam(name = "first-name") String firstName,
            @RequestParam(name = "last-name") String lastName,
            @RequestParam(name = "patronymic", required = false) String patronymic) {
        return this.studentService.findByFullName(firstName, lastName, patronymic);
    }

    @GetMapping(path = "/find-all")
    public List<StudentDTO> findAll() {
        return this.studentService.findAll();
    }

    @GetMapping(path = "/find-by-group")
    public List<StudentDTO> findByGroup(@RequestParam(name = "group-id") Integer groupId) {
        return this.studentService.findByGroup(groupId);
    }

    @GetMapping(path = "/find-by-health-group")
    public List<StudentDTO> findByHealthGroup(@RequestParam(name = "health-group-id") Integer healthGroupId) {
        return this.studentService.findByHealthGroup(healthGroupId);
    }

    @GetMapping(path = "/find-by-section")
    public List<StudentDTO> findBySection(@RequestParam(name = "section-id") Integer sectionId) {
        return this.studentService.findBySection(sectionId);
    }

    @GetMapping(path = "/is-exists-by-login")
    public boolean isExistByLogin(@RequestParam(name = "login") String login) {
        return this.studentService.existsByLogin(login);
    }

    @PostMapping(path = "/create")
    public ResponseEntity<StudentDTO> create(@RequestBody StudentCreationDTO dto) {
        StudentDTO created = studentService.create(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
