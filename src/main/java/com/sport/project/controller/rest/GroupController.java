package com.sport.project.controller.rest;

import com.sport.project.dto.GroupCreationDTO;
import com.sport.project.dto.GroupDTO;
import com.sport.project.dto.StudentDTO;
import com.sport.project.service.GroupService;
import com.sport.project.service.StudentService;
import com.sport.project.service.interfaces.group.GroupCreationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST контроллер для управления учебными группами
 */
@Tag(name = "Группы", description = "API для управления учебными группами")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/group")
public class GroupController {

    private final GroupService groupService;
    private final StudentService studentService;
    private final GroupCreationService groupCreationService;

    @Operation(summary = "Создание группы", description = "Возвращает созданную группу в виде JSON")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Группа создана"),
            @ApiResponse(responseCode = "400", description = "Группа не создана")
    })
    @PostMapping("/create")
    public ResponseEntity<GroupDTO> create(@RequestBody GroupCreationDTO groupCreationDTO) {
        GroupDTO created = groupCreationService.create(groupCreationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Получить все группы", description = "Возвращает список всех учебных групп")
    @ApiResponse(responseCode = "200", description = "Группы успешно получены")
    @GetMapping("/get-all")
    public ResponseEntity<List<GroupDTO>> getAll() {
        return ResponseEntity.ok(groupService.findAll());
    }

    @Operation(summary = "Получить группу по ID", description = "Возвращает учебную группу по её уникальному идентификатору")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Группа найдена"),
        @ApiResponse(responseCode = "404", description = "Группа не найдена")
    })
    @GetMapping("/{id}")
    public ResponseEntity<GroupDTO> getGroupById(
            @Parameter(description = "ID группы", example = "1")
            @PathVariable("id") Integer id) {
        GroupDTO group = groupService.findById(id);
        return ResponseEntity.ok(group);
    }

    @Operation(summary = "Получить группу по названию", description = "Возвращает учебную группу по её названию")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Группа найдена"),
        @ApiResponse(responseCode = "404", description = "Группа не найдена")
    })
    @GetMapping("/by-name")
    public ResponseEntity<GroupDTO> getGroupByName(
            @Parameter(description = "Название группы", example = "ИУ7-12Б")
            @RequestParam String name) {
        return ResponseEntity.ok(groupService.findByName(name));
    }

    @Operation(summary = "Получить группы по институту", description = "Возвращает список групп по названию института")
    @ApiResponse(responseCode = "200", description = "Группы найдены")
    @GetMapping("/by-institute")
    public ResponseEntity<List<GroupDTO>> getGroupByInstitute(
            @Parameter(description = "Название института", example = "ИУ")
            @RequestParam String institute) {
        return ResponseEntity.ok(groupService.findByInstitute(institute));
    }

    @Operation(summary = "Получить студентов группы по ID", description = "Возвращает список студентов учебной группы по её ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Студенты найдены"),
        @ApiResponse(responseCode = "404", description = "Группа не найдена")
    })
    @GetMapping("/{groupId}/students")
    public ResponseEntity<List<StudentDTO>> getStudentsByGroupId(
            @Parameter(description = "ID группы", example = "1")
            @PathVariable(name = "groupId") Integer groupId) {
        return ResponseEntity.ok(groupService.getStudents(groupId));
    }


    @Operation(summary = "Получить студентов группы по названию", description = "Возвращает список студентов учебной группы по её названию")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Студенты найдены"),
        @ApiResponse(responseCode = "404", description = "Группа не найдена")
    })
    @GetMapping("/{groupName}/students")
    public ResponseEntity<List<StudentDTO>> getStudentsByGroupName(
            @Parameter(description = "Название группы", example = "ИУ7-12Б")
            @PathVariable(name = "groupName") String groupName) {
        return ResponseEntity.ok(groupService.getStudents(groupName));
    }

}
