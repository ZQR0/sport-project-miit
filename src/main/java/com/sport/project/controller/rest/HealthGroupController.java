package com.sport.project.controller.rest;

import com.sport.project.dto.HealthGroupCreationDTO;
import com.sport.project.dto.HealthGroupDTO;
import com.sport.project.dto.StudentDTO;
import com.sport.project.exception.EntityAlreadyExistsException;
import com.sport.project.service.impl.HealthGroupServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/healthGroup")
@RequiredArgsConstructor
public class HealthGroupController {

    private final HealthGroupServiceImpl healthGroupService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> findByID(@PathVariable(name = "id") Integer id) throws EntityNotFoundException {
        HealthGroupDTO healthGroup = healthGroupService.findById(id);
        return ResponseEntity.ok(healthGroup);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> findByName(@PathVariable(name = "name") String name) throws EntityNotFoundException {
        HealthGroupDTO healthGroup = healthGroupService.findByName(name);
        return ResponseEntity.ok(healthGroup);
    }

    @GetMapping(path = "/find-all")
    public ResponseEntity<?> findAll() {
        List<HealthGroupDTO> healthGroups = healthGroupService.findAll();
        return ResponseEntity.ok(healthGroups);
    }

    @GetMapping(path = "/{id}/students")
    public ResponseEntity<?> getStudentsById(@PathVariable(name = "id") Integer healthGroupId)
            throws EntityNotFoundException {
        List<StudentDTO> students = healthGroupService.getStudents(healthGroupId);
        return ResponseEntity.ok(students);
    }

    @GetMapping(path = "/name/{name}/students")
    public ResponseEntity<?> getStudentsByName(@PathVariable(name = "name") String healthGroupName)
            throws EntityNotFoundException {
        List<StudentDTO> students = healthGroupService.getStudents(healthGroupName);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/exists/name/{name}")
    public ResponseEntity<?> existsByName(@PathVariable(name = "name") String name) {
        boolean exists = healthGroupService.existsByName(name);
        return ResponseEntity.ok(exists);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody HealthGroupCreationDTO dto) throws EntityAlreadyExistsException {
        HealthGroupDTO healthGroup = healthGroupService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(healthGroup);
    }
}
