package com.sport.project.controller.rest;

import com.sport.project.dto.GroupDTO;
import com.sport.project.dto.StudentDTO;
import com.sport.project.service.GroupService;
import com.sport.project.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/group")
public class GroupController {

    private final GroupService groupService;
    private final StudentService studentService;

    //Получить все группы
    @GetMapping
    public ResponseEntity<List<GroupDTO>> getAll() {
        return ResponseEntity.ok(groupService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupDTO> getGroupById(@PathVariable Integer id) {
        GroupDTO group = groupService.findById(id);
        return ResponseEntity.ok(group);
    }

    @GetMapping("/by-name")
    public ResponseEntity<GroupDTO> getGroupByName(@RequestParam String name) {
        return ResponseEntity.ok(groupService.findByName(name));
    }

    @GetMapping("/by-institute")
    public ResponseEntity<List<GroupDTO>> getGroupByInstitute(@RequestParam String institute) {
        return ResponseEntity.ok(groupService.findByInstitute(institute));
    }

    @GetMapping("/{groupId}/students")
    public ResponseEntity<List<StudentDTO>> getStudentsByGroupId (@PathVariable Integer groupId) {
        return ResponseEntity.ok(groupService.getStudents(groupId));
    }



}
