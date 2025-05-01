package com.sport.project.config;

import com.sport.project.dto.TeacherDTO;
import com.sport.project.exception.EntityNotFoundException;
import com.sport.project.service.impl.TeacherServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(path = "/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherServiceImpl service;

    @GetMapping(path = "/find-by-id", params = "id")
    public String findById(@RequestParam(name = "id") Integer id, Model model) throws EntityNotFoundException {
        TeacherDTO teacher = this.service.findById(id);
        model.addAttribute("teacher", teacher);
        return "teacher";
    }

    @GetMapping(path = "/find-by-id", params = "login")
    public String findByLogin(@RequestParam(name = "login") String login, Model model) throws EntityNotFoundException {
        TeacherDTO teacher = this.service.findByLogin(login);
        model.addAttribute("teacher", teacher);
        return "teacher";
    }

    @GetMapping(path = "/find-by-id", params = "fsp")
    public String findByFSP(@RequestParam(name = "fsp") String fsp, Model model) throws EntityNotFoundException {
        TeacherDTO teacher = this.service.findByLogin(fsp);
        model.addAttribute("teacher", teacher);
        return "teacher";
    }

    @GetMapping(path = "/find-all")
    public String findAll(Model model) {
        List<TeacherDTO> teachers = this.service.findAll();
        model.addAttribute("teachers", teachers);
        return "teachers";
    }

    @GetMapping(path = "/find-all-moderators")
    public String findAllModerators(Model model) {
        List<TeacherDTO> teachers = this.service.findAllModerators();
        model.addAttribute("teachers", teachers);
        return "teachers";
    }
}
