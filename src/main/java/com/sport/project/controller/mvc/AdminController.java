package com.sport.project.controller.mvc;

import com.sport.project.dto.*;
import com.sport.project.exception.EntityAlreadyExistsException;
import com.sport.project.exception.EntityNotFoundException;
import com.sport.project.service.*;
import com.sport.project.service.interfaces.discipline.DisciplineCreationService;
import com.sport.project.service.interfaces.group.GroupCreationService;
import com.sport.project.service.interfaces.healthgroup.HealthGroupCreationService;
import com.sport.project.service.interfaces.lesson.LessonCreationService;
import com.sport.project.service.interfaces.section.SectionCreationService;
import com.sport.project.service.interfaces.student.StudentCreationService;
import com.sport.project.service.interfaces.teacher.TeacherCreationService;
import com.sport.project.service.interfaces.visit.VisitCreationService;
import com.sport.project.service.interfaces.visit.VisitUpdatingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final StudentService studentService;
    private final StudentCreationService studentCreationService;
    private final TeacherService teacherService;
    private final TeacherCreationService teacherCreationService;
    private final GroupService groupService;
//    private final GroupCreationService groupCreationService;
    private final HealthGroupService healthGroupService;
    private final HealthGroupCreationService healthGroupCreationService;
    private final SectionService sectionService;
    private final SectionCreationService sectionCreationService;
    private final DisciplineService disciplineService;
    private final DisciplineCreationService disciplineCreationService;
    private final LessonService lessonService;
    private final LessonCreationService lessonCreationService;
    private final VisitService visitService;
    private final VisitCreationService visitCreationService;
    private final VisitUpdatingService visitUpdatingService;

    // ==================== DASHBOARD ====================
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("studentsCount", studentService.findAll().size());
        model.addAttribute("teachersCount", teacherService.findAll().size());
        model.addAttribute("groupsCount", groupService.findAll().size());
        model.addAttribute("healthGroupsCount", healthGroupService.findAll().size());
        model.addAttribute("sectionsCount", sectionService.findAll().size());
        model.addAttribute("disciplinesCount", disciplineService.findAll().size());
        model.addAttribute("lessonsCount", lessonService.findAll().size());
        model.addAttribute("visitsCount", visitService.findAll().size());
        return "admin/dashboard";
    }

    // ==================== STUDENTS ====================
    @GetMapping("/students")
    public String studentsPage(Model model) {
        List<StudentDTO> students = studentService.findAll();
        List<HealthGroupDTO> healthGroups = healthGroupService.findAll();
        List<GroupDTO> groups = groupService.findAll();
        model.addAttribute("students", students);
        model.addAttribute("healthGroups", healthGroups);
        model.addAttribute("groups", groups);
        return "admin/students";
    }

    @PostMapping("/students/create")
    public String createStudent(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam(required = false) String patronymic,
            @RequestParam String login,
            @RequestParam String password,
            @RequestParam Integer healthGroupId,
            @RequestParam Integer groupId,
            @RequestParam(required = false) String birthday,
            RedirectAttributes redirectAttributes
    ) {
        try {
            StudentCreationDTO dto = StudentCreationDTO.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .patronymic(patronymic)
                    .login(login)
                    .password(password)
                    .healthGroupId(healthGroupId)
                    .groupId(groupId)
                    .birthday(birthday != null && !birthday.isEmpty() ? LocalDate.parse(birthday) : null)
                    .build();
            studentCreationService.create(dto);
            redirectAttributes.addFlashAttribute("successMessage", "Студент успешно создан");
        } catch (EntityAlreadyExistsException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Студент с таким логином уже существует");
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Группа здоровья или учебная группа не найдена");
        } catch (Exception e) {
            log.error("Error creating student", e);
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка при создании студента: " + e.getMessage());
        }
        return "redirect:/admin/students";
    }

    // ==================== TEACHERS ====================
    @GetMapping("/teachers")
    public String teachersPage(Model model) {
        List<TeacherDTO> teachers = teacherService.findAll();
        model.addAttribute("teachers", teachers);
        return "admin/teachers";
    }

    @PostMapping("/teachers/create")
    public String createTeacher(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam(required = false) String patronymic,
            @RequestParam String login,
            @RequestParam String password,
            @RequestParam(defaultValue = "false") boolean moderator,
            @RequestParam(required = false) String birthday,
            RedirectAttributes redirectAttributes
    ) {
        try {
            TeacherCreationDTO dto = TeacherCreationDTO.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .patronymic(patronymic)
                    .login(login)
                    .password(password)
                    .moderator(moderator)
                    .birthday(birthday != null && !birthday.isEmpty() ? LocalDate.parse(birthday) : null)
                    .build();
            teacherCreationService.create(dto);
            redirectAttributes.addFlashAttribute("successMessage", "Преподаватель успешно создан");
        } catch (EntityAlreadyExistsException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Преподаватель с таким логином уже существует");
        } catch (Exception e) {
            log.error("Error creating teacher", e);
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка при создании преподавателя: " + e.getMessage());
        }
        return "redirect:/admin/teachers";
    }

    // ==================== GROUPS ====================
    @GetMapping("/groups")
    public String groupsPage(Model model) {
        List<GroupDTO> groups = groupService.findAll();
        model.addAttribute("groups", groups);
        return "admin/groups";
    }

//    @PostMapping("/groups/create")
//    public String createGroup(
//            @RequestParam String name,
//            @RequestParam String institute,
//            RedirectAttributes redirectAttributes
//    ) {
//        try {
//            GroupCreationDTO dto = GroupCreationDTO.builder()
//                    .name(name)
//                    .institute(institute)
//                    .build();
//            groupCreationService.create(dto);
//            redirectAttributes.addFlashAttribute("successMessage", "Группа успешно создана");
//        } catch (EntityAlreadyExistsException e) {
//            redirectAttributes.addFlashAttribute("errorMessage", "Группа с таким названием уже существует");
//        } catch (Exception e) {
//            log.error("Error creating group", e);
//            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка при создании группы: " + e.getMessage());
//        }
//        return "redirect:/admin/groups";
//    }

    // ==================== HEALTH GROUPS ====================
    @GetMapping("/health-groups")
    public String healthGroupsPage(Model model) {
        List<HealthGroupDTO> healthGroups = healthGroupService.findAll();
        model.addAttribute("healthGroups", healthGroups);
        return "admin/health-groups";
    }

    @PostMapping("/health-groups/create")
    public String createHealthGroup(
            @RequestParam String name,
            @RequestParam String description,
            RedirectAttributes redirectAttributes
    ) {
        try {
            HealthGroupCreationDTO dto = HealthGroupCreationDTO.builder()
                    .name(name)
                    .description(description)
                    .build();
            healthGroupCreationService.create(dto);
            redirectAttributes.addFlashAttribute("successMessage", "Медицинская группа успешно создана");
        } catch (EntityAlreadyExistsException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Медицинская группа с таким названием уже существует");
        } catch (Exception e) {
            log.error("Error creating health group", e);
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка при создании медицинской группы: " + e.getMessage());
        }
        return "redirect:/admin/health-groups";
    }

    // ==================== SECTIONS ====================
    @GetMapping("/sections")
    public String sectionsPage(Model model) {
        List<SectionDTO> sections = sectionService.findAll();
        model.addAttribute("sections", sections);
        return "admin/sections";
    }

    @PostMapping("/sections/create")
    public String createSection(
            @RequestParam String name,
            @RequestParam String description,
            RedirectAttributes redirectAttributes
    ) {
        try {
            SectionCreationDTO dto = SectionCreationDTO.builder()
                    .name(name)
                    .description(description)
                    .build();
            sectionCreationService.create(dto);
            redirectAttributes.addFlashAttribute("successMessage", "Секция успешно создана");
        } catch (EntityAlreadyExistsException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Секция с таким названием уже существует");
        } catch (Exception e) {
            log.error("Error creating section", e);
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка при создании секции: " + e.getMessage());
        }
        return "redirect:/admin/sections";
    }

    // ==================== DISCIPLINES ====================
    @GetMapping("/disciplines")
    public String disciplinesPage(Model model) {
        List<DisciplineDTO> disciplines = disciplineService.findAll();
        model.addAttribute("disciplines", disciplines);
        return "admin/disciplines";
    }

    @PostMapping("/disciplines/create")
    public String createDiscipline(
            @RequestParam String name,
            RedirectAttributes redirectAttributes
    ) {
        try {
            DisciplineCreationDTO dto = DisciplineCreationDTO.builder()
                    .name(name)
                    .build();
            disciplineCreationService.create(dto);
            redirectAttributes.addFlashAttribute("successMessage", "Дисциплина успешно создана");
        } catch (EntityAlreadyExistsException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Дисциплина с таким названием уже существует");
        } catch (Exception e) {
            log.error("Error creating discipline", e);
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка при создании дисциплины: " + e.getMessage());
        }
        return "redirect:/admin/disciplines";
    }

    // ==================== LESSONS ====================
    @GetMapping("/lessons")
    public String lessonsPage(Model model) {
        List<LessonDTO> lessons = lessonService.findAll();
        List<TeacherDTO> teachers = teacherService.findAll();
        List<DisciplineDTO> disciplines = disciplineService.findAll();
        model.addAttribute("lessons", lessons);
        model.addAttribute("teachers", teachers);
        model.addAttribute("disciplines", disciplines);
        return "admin/lessons";
    }

    @PostMapping("/lessons/create")
    public String createLesson(
            @RequestParam String dateOfLesson,
            @RequestParam Integer teacherId,
            @RequestParam Integer disciplineId,
            @RequestParam String startAt,
            @RequestParam String endAt,
            RedirectAttributes redirectAttributes
    ) {
        try {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

            LocalDate date = LocalDate.parse(dateOfLesson, dateFormatter);
            LocalTime startTime = LocalTime.parse(startAt, timeFormatter);
            LocalTime endTime = LocalTime.parse(endAt, timeFormatter);

            LessonCreationDTO dto = LessonCreationDTO.builder()
                    .dateOfLesson(date)
                    .teacherId(teacherId)
                    .disciplineId(disciplineId)
                    .startAt(startTime)
                    .endAt(endTime)
                    .build();
            lessonCreationService.create(dto);
            redirectAttributes.addFlashAttribute("successMessage", "Занятие успешно создано");
        } catch (DateTimeParseException e) {
            log.error("Error parsing date/time", e);
            redirectAttributes.addFlashAttribute("errorMessage", "Неверный формат даты или времени");
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Преподаватель или дисциплина не найдены");
        } catch (Exception e) {
            log.error("Error creating lesson", e);
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка при создании занятия: " + e.getMessage());
        }
        return "redirect:/admin/lessons";
    }

    // ==================== VISITS ====================
    @GetMapping("/visits")
    public String visitsPage(Model model) {
        List<VisitDTO> visits = visitService.findAll();
        List<StudentDTO> students = studentService.findAll();
        List<LessonDTO> lessons = lessonService.findAll();
        model.addAttribute("visits", visits);
        model.addAttribute("students", students);
        model.addAttribute("lessons", lessons);
        return "admin/visits";
    }

    @PostMapping("/visits/create")
    public String createVisit(
            @RequestParam String studentLogin,
            @RequestParam Integer lessonId,
            @RequestParam(defaultValue = "true") boolean isExists,
            RedirectAttributes redirectAttributes
    ) {
        try {
            VisitDTO dto = visitCreationService.create(studentLogin, lessonId, isExists);
            redirectAttributes.addFlashAttribute("successMessage", "Посещение успешно создано");
        } catch (EntityAlreadyExistsException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Посещение уже существует");
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Студент или занятие не найдены");
        } catch (Exception e) {
            log.error("Error creating visit", e);
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка при создании посещения: " + e.getMessage());
        }
        return "redirect:/admin/visits";
    }

    @PostMapping("/visits/update/{id}")
    public String updateVisit(
            @PathVariable Integer id,
            @RequestParam boolean isExists,
            RedirectAttributes redirectAttributes
    ) {
        try {
            visitUpdatingService.updateStatus(id, isExists);
            redirectAttributes.addFlashAttribute("successMessage", "Статус посещения обновлен");
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Посещение не найдено");
        } catch (Exception e) {
            log.error("Error updating visit", e);
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка при обновлении посещения: " + e.getMessage());
        }
        return "redirect:/admin/visits";
    }
}
