package com.sport.project.controller.mvc;

import com.sport.project.dto.StudentDTO;
import com.sport.project.exception.EntityNotFoundException;
import com.sport.project.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Slf4j
public class StudentLoginController {

    private final StudentService studentService;

    @GetMapping("/student-by-login")
    public String showSearchPage(Model model) {
        return "student-by-login";
    }

    @PostMapping("/student-by-login")
    public String findStudent(@RequestParam String login, Model model) {
        try {
            StudentDTO student = studentService.findByLogin(login);
            model.addAttribute("student", student);
            model.addAttribute("searchLogin", login);
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", "Студент с логином '" + login + "' не найден");
            model.addAttribute("searchLogin", login);
        }
        return "student-by-login";
    }
}
