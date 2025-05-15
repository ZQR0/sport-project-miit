package com.sport.project.controller;

import com.sport.project.dto.StudentCreationDTO;
import com.sport.project.dto.StudentDTO;
import com.sport.project.dto.TeacherDTO;
import com.sport.project.exception.EntityAlreadyExistsException;
import com.sport.project.exception.EntityNotFoundException;
import com.sport.project.service.StudentService;
import com.sport.project.service.impl.StudentServiceImpl;
import com.sport.project.service.impl.TeacherServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(path = "/students")
@RequiredArgsConstructor
@Slf4j
public class StudentController {

    private final StudentServiceImpl studentService;
    private final TeacherServiceImpl teacherService;

    @GetMapping(path = "find-by-id", params = "id", produces = MediaType.TEXT_HTML_VALUE)
    public String findStudentByIdEndpoint(Model model, @RequestParam(name = "id") Integer id, HttpServletResponse response)
            throws EntityNotFoundException
    {
        try {
            StudentDTO student = this.studentService.findById(id);
            model.addAttribute("student", student);
            return "student_profile";
        } catch (EntityNotFoundException e) {
            log.info(e.getMessage());
            try {
                response.sendRedirect("/error");
            } catch (IOException ex) {
                log.info(ex.getMessage());
            }
        }

        return null;
    }

    @GetMapping(path = "find-by-login", params = "login", produces = MediaType.TEXT_HTML_VALUE)
    public String findStudentByLoginEndpoint(Model model, @RequestParam(name = "login") String login, HttpServletResponse response)
            throws EntityNotFoundException
    {
        try {
            StudentDTO student = this.studentService.findByLogin(login);
            TeacherDTO teacherOfStudent = this.teacherService.findById(student.getTeacherId());

            model.addAttribute("student", student);
            model.addAttribute("teacher", teacherOfStudent);
            return "student_profile";
        } catch (EntityNotFoundException e) {
            log.info(e.getMessage());
            try {
                response.sendRedirect("/error");
            } catch (IOException ex) {
                log.info(ex.getMessage());
            }
        }

        return null;
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

    @GetMapping(path = "find-all", produces = MediaType.TEXT_HTML_VALUE)
    public String getAllStudentsEndpoint(Model model) {
        List<StudentDTO> students = this.studentService.findAll();
        model.addAttribute("students", students);
        return "students";
    }

    @PostMapping(path = "create")
    public void createStudent(StudentCreationDTO studentDTO, HttpServletResponse response) throws EntityAlreadyExistsException {
        StudentDTO created = this.studentService.createStudent(studentDTO);
        String login = studentDTO.getLogin();
        try {
            response.sendRedirect(String.format("/students/find-by-login?login=%s", login));
        } catch (IOException ex) {
            log.info(ex.getMessage());
        }
    }
}
