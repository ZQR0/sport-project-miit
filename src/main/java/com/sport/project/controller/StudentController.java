package com.sport.project.controller;

import com.sport.project.dto.StudentDTO;
import com.sport.project.exception.EntityNotFoundException;
import com.sport.project.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/students")
@RequiredArgsConstructor
@Slf4j
public class StudentController {

    private final StudentService studentService;

    @GetMapping(path = "find-by-id", params = "id", produces = MediaType.TEXT_HTML_VALUE)
    public String findStudentByIdEndpoint(Model model, @RequestParam(name = "id") Integer id)
            throws EntityNotFoundException
    {
        StudentDTO student = this.studentService.findById(id);
        model.addAttribute("student", student);
        return "student_profile";
    }

    @GetMapping(path = "find-by-login", params = "login", produces = MediaType.TEXT_HTML_VALUE)
    public String findStudentByLoginEndpoint(Model model, @RequestParam(name = "login") String login)
            throws EntityNotFoundException
    {
        StudentDTO student = this.studentService.findByLogin(login);
        model.addAttribute("student", student);
        return "student_profile";
    }

    @GetMapping(path = "find-by-fsp", params = "fsp", produces = MediaType.TEXT_HTML_VALUE)
    public String findStudentByFSPEndpoint(Model model, @RequestParam(name = "fsp") String fsp)
            throws EntityNotFoundException
    {
        String newFsp = fsp.replaceAll("_", " ");
        StudentDTO student = this.studentService.findByFSP(newFsp);
        model.addAttribute("student", student);
        return "student_profile";
    }

    @GetMapping(path = "find-all")
    public String getAllStudentsEndpoint(Model model) {
        List<StudentDTO> students = this.studentService.findAll();
        model.addAttribute("students", students);
        return "students";
    }


    @PostMapping(path = "create")
    public String createStudent(Model model, @ModelAttribute("student") StudentDTO studentDTO){
        return "";
    }
}
