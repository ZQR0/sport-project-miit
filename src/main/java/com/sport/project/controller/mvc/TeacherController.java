package com.sport.project.controller;

import com.sport.project.dto.TeacherCreationDTO;
import com.sport.project.dto.TeacherDTO;
import com.sport.project.dto.UserDetailsImpl;
import com.sport.project.exception.EntityAlreadyExistsException;
import com.sport.project.exception.EntityNotFoundException;
import com.sport.project.service.impl.TeacherServiceImpl;
import com.sport.project.utils.AuthorizedUserUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping(path = "/teachers")
@RequiredArgsConstructor
@Slf4j
public class TeacherController {

    private final TeacherServiceImpl service;

//    @GetMapping(path = "/find-by-id", params = "id")
//    public String findById(@RequestParam(name = "id") Integer id, Model model, HttpServletResponse response) {
//        try {
//            TeacherDTO teacher = this.service.findById(id);
//            model.addAttribute("teacher", teacher);
//            return "teacher_page";
//        } catch (EntityNotFoundException ex) {
//            log.info(ex.getMessage());
//            try {
//                response.sendRedirect("/error");
//            } catch (IOException exception) {
//                log.info(exception.getMessage());
//            }
//        }
//
//        return null;
//    }

    @GetMapping(path = "/find-by-login", params = "login")
    public String findByLogin(@RequestParam(name = "login") String login, Model model, HttpServletResponse response) {
        try {
            TeacherDTO teacher = this.service.findByLogin(login);
            model.addAttribute("teacher", teacher);
            return "teacher_page";
        } catch (EntityNotFoundException ex) {
            log.info(ex.getMessage());
            try {
                response.sendRedirect("/error");
            } catch (IOException exception) {
                log.info(exception.getMessage());
            }
        }

        return null;
    }

    @GetMapping(path = "/find-by-fsp", params = "fsp")
    public String findByFSP(@RequestParam(name = "fsp") String fsp, Model model) throws EntityNotFoundException {
        TeacherDTO teacher = this.service.findByLogin(fsp);
        model.addAttribute("teacher", teacher);
        return "teacher_page";
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

    @PostMapping(path = "create", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void createEndpoint(TeacherCreationDTO dto, HttpServletResponse response)
            throws EntityAlreadyExistsException
    {
        TeacherDTO created = this.service.createTeacher(dto);
        try {
            response.sendRedirect(String.format("/teachers/find-by-login?login=%s", dto.getLogin()));
        } catch (IOException ex) {
            log.info(ex.getMessage());
        }
    }

    @PostMapping(path = "/update-schedule", params = {"date", "lesson"})
    public void updateSchedule(
            @RequestParam(name = "date") String rawDate,
            @RequestParam(name = "lesson") String lesson,
            HttpServletResponse response) {

        // Преобразование строки даты в LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.parse(rawDate, formatter);

        // Передача дат и уроков сервису
        service.updateSchedule(date, lesson);

        // Получаем логин текущего пользователя
        String login = AuthorizedUserUtils.getCurrentUser().getLogin();

        // Перенаправляем обратно на профиль учителя
        try {
            response.sendRedirect(String.format("/teachers/find-by-login?login=%s", login));
        } catch (IOException ex) {
            log.info(ex.getMessage());
        }
    }

    @PostMapping(path = "/notice")
    public void noticeEndpoint(HttpServletResponse response, @RequestParam(name = "login") String login)
        throws EntityNotFoundException
    {
        if (this.service.noticeStudent(login)) {
            try {
                response.sendRedirect(String.format("/students/find-by-login?login=%s", login));
            } catch (IOException ex) {
                log.info(ex.getMessage());
            }
        } else {
            log.error("Can not notice student");
        }
    }
}
