package com.sport.project.controller;

import com.sport.project.dao.entity.BaseEntity;
import com.sport.project.dto.StudentDTO;
import com.sport.project.dto.TeacherDTO;
import com.sport.project.service.StudentService;
import com.sport.project.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping(path = "/admin")
@RequiredArgsConstructor
public class AdminController {

    private final TeacherService teacherService;
    private final StudentService studentService;

    @GetMapping(path = "dashboard")
    public String dashboardEndpoint(Model model) {
//        List<TeacherDTO> teacherDTOS = this.teacherService.findAll();
//        List<StudentDTO> studentDTOS = this.studentService.findAll();
//
//        model.addAttribute("teachersList", teacherDTOS);
//        model.addAttribute("studentsList", studentDTOS);

        return "admin_page";
    }

    @GetMapping(path = "dashboard/teachers")
    public String getAllTeachers(Model model) {
        List<TeacherDTO> teachers = this.teacherService.findAll();

        model.addAttribute("teachersList");

        return "admin_teachers";
    }

    @GetMapping(path = "dashboard/students")
    public String getAllStudents(Model model) {
        List<StudentDTO> teachers = this.studentService.findAll();

        model.addAttribute("studentsList");

        return "admin_teachers";
    }

}
