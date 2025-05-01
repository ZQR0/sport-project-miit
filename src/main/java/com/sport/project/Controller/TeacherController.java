package com.sport.project.Controller;

import com.sport.project.dto.TeacherDTO;
import com.sport.project.exception.EntityNotFoundException;
import com.sport.project.service.impl.TeacherServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherServiceImpl service;

    @GetMapping(path = "/findById", params = "id")
    public String findById(@RequestParam(name = "id") Integer id, Model model) throws EntityNotFoundException {
        TeacherDTO teacher = this.service.findById(id);
        model.addAttribute("teacher", teacher);
        return "teacher";
    }

    @GetMapping(path = "/findByLogin", params = "login")
    public String findByLogin(@RequestParam(name = "login") String login, Model model) throws EntityNotFoundException {
        TeacherDTO teacher = this.service.findByLogin(login);
        model.addAttribute("teacher", teacher);
        return "teacher";
    }

    @GetMapping(path = "/findByFSP", params = "fsp")
    public String findByFSP(@RequestParam(name = "fsp") String fsp, Model model) throws EntityNotFoundException {
        TeacherDTO teacher = this.service.findByLogin(fsp);
        model.addAttribute("teacher", teacher);
        return "teacher";
    }

    @GetMapping(path = "/findAll/")
    public String findAll (Model model) {
        List<TeacherDTO> teachers = this.service.findAll();
        model.addAttribute("teachers", teachers);
        return "teachers";
    }

    @GetMapping(path = "/findAllModerator/")
    public String findAllModerators (Model model) {
        List<TeacherDTO> teachers = this.service.findAllModerators();
        model.addAttribute("teachers", teachers);
        return "teachers";
    }
}
