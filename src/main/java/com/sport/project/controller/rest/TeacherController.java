package com.sport.project.controller.rest;

import com.sport.project.dto.TeacherCreationDTO;
import com.sport.project.dto.TeacherDTO;
import com.sport.project.service.impl.TeacherServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "/teachers")
@RequiredArgsConstructor
@Slf4j
public class TeacherController {

    private final TeacherServiceImpl teacherService;

    @GetMapping(path = "/find-by-login", params = "login")
    public TeacherDTO findByLogin(@RequestParam(name = "login") String login) {
        return this.teacherService.findByLogin(login);
    }

    @GetMapping(path = "/find-by-full-name")
    public List<TeacherDTO> findByFullName(
            @RequestParam(name = "first-name") String firstName,
            @RequestParam(name = "last-name") String lastName,
            @RequestParam(name = "patronymic", required = false) String patronymic) {
        return this.teacherService.findByFullName(firstName, lastName, patronymic);
    }

    @GetMapping(path = "/find-all")
    public List<TeacherDTO> findAll() {
        return this.teacherService.findAll();
    }

    @GetMapping(path = "/find-all-moderators")
    public List<TeacherDTO> findAllModerators() {
        return this.teacherService.findAllModerators();
    }

    @GetMapping(path = "/get-by-lesson-date")
    public List<TeacherDTO> findByLessonDate(@RequestParam(name = "date") LocalDate date) {
        return this.teacherService.findByLessonsDate(date);
    }

    @GetMapping(path = "/is-exists-by-login")
    public boolean isExistByLogin(@RequestParam(name = "login") String login) {
        return this.teacherService.existsByLogin(login);
    }

    @PostMapping(path = "/create")
    public ResponseEntity<TeacherDTO> create(@RequestBody TeacherCreationDTO dto) {
        TeacherDTO created = teacherService.create(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }


//    @PostMapping(path = "/update-schedule", params = {"date", "lesson"})
//    public void updateSchedule(
//            @RequestParam(name = "date") String rawDate,
//            @RequestParam(name = "lesson") String lesson,
//            HttpServletResponse response) {
//
//        // Преобразование строки даты в LocalDate
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//        LocalDate date = LocalDate.parse(rawDate, formatter);
//
//        // Передача дат и уроков сервису
//        teacherService.updateSchedule(date, lesson);
//
//        // Получаем логин текущего пользователя
//        String login = AuthorizedUserUtils.getCurrentUser().getLogin();
//
//        // Перенаправляем обратно на профиль учителя
//        try {
//            response.sendRedirect(String.format("/teachers/find-by-login?login=%s", login));
//        } catch (IOException ex) {
//            log.info(ex.getMessage());
//        }
//    }
//
//    @PostMapping(path = "/notice")
//    public void noticeEndpoint(HttpServletResponse response, @RequestParam(name = "login") String login)
//            throws EntityNotFoundException {
//        if (this.teacherService.noticeStudent(login)) {
//            try {
//                response.sendRedirect(String.format("/students/find-by-login?login=%s", login));
//            } catch (IOException ex) {
//                log.info(ex.getMessage());
//            }
//        } else {
//            log.error("Can not notice student");
//        }
//    }
}
