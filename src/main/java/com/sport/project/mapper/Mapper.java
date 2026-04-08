package com.sport.project.mapper;

import com.sport.project.dao.entity.DisciplineEntity;
import com.sport.project.dao.entity.GroupEntity;
import com.sport.project.dao.entity.HealthGroupsEntity;
import com.sport.project.dao.entity.LessonsEntity;
import com.sport.project.dao.entity.SectionEntity;
import com.sport.project.dao.entity.StudentEntity;
import com.sport.project.dao.entity.TeacherEntity;
import com.sport.project.dao.entity.VisitsEntity;
import com.sport.project.dto.DisciplineDTO;
import com.sport.project.dto.GroupDTO;
import com.sport.project.dto.HealthGroupDTO;
import com.sport.project.dto.LessonDTO;
import com.sport.project.dto.SectionDTO;
import com.sport.project.dto.StudentDTO;
import com.sport.project.dto.TeacherDTO;
import com.sport.project.dto.UserDetailsImpl;
import com.sport.project.dto.VisitDTO;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Утилитарный класс для преобразования сущностей в DTO и обратно.
 */
public class Mapper {

    private Mapper() {
        // Utility class
    }

    /**
     * Преобразование StudentEntity в StudentDTO.
     *
     * @param entity сущность студента
     * @return DTO студента
     */
    public static StudentDTO map(StudentEntity entity) {
        if (entity == null) {
            return null;
        }

        Map<LocalDate, Boolean> exist = entity.getVisits() != null
                ? entity.getVisits().stream()
                    .collect(Collectors.toMap(
                            v -> v.getLessons().getDateOfLesson(),
                            VisitsEntity::isExists,
                            (v1, v2) -> v1
                    ))
                : Collections.emptyMap();

        return StudentDTO.builder()
                .id(entity.getId())
                .firstName(entity.getFullName().getFirstName())
                .lastName(entity.getFullName().getLastName())
                .patronymic(entity.getFullName().getPatronymic())
                .login(entity.getLogin())
                .healthGroup(entity.getHealthGroup().getId())
                .exist(exist)
                .build();
    }

    /**
     * Преобразование TeacherEntity в TeacherDTO.
     *
     * @param entity сущность преподавателя
     * @return DTO преподавателя
     */
    public static TeacherDTO map(TeacherEntity entity) {
        if (entity == null) {
            return null;
        }

        Map<LocalDate, String> schedule = entity.getLessons() != null
                ? entity.getLessons().stream()
                    .collect(Collectors.toMap(
                            LessonsEntity::getDateOfLesson,
                            l -> l.getDiscipline().getName(),
                            (v1, v2) -> v1 + ", " + v2
                    ))
                : Collections.emptyMap();

        List<StudentDTO> students = Collections.emptyList();

        return TeacherDTO.builder()
                .id(entity.getId())
                .firstName(entity.getFullName().getFirstName())
                .lastName(entity.getFullName().getLastName())
                .patronymic(entity.getFullName().getPatronymic())
                .login(entity.getLogin())
                .passwordHash(entity.getPasswordHash())
                .moderator(entity.isModerator())
                .schedule(schedule)
                .students(students)
                .build();
    }

    /**
     * Преобразование GroupEntity в GroupDTO.
     *
     * @param entity сущность группы
     * @return DTO группы
     */
    public static GroupDTO map(GroupEntity entity) {
        if (entity == null) {
            return null;
        }

        return GroupDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .institute(entity.getInstitute())
                .build();
    }

    /**
     * Преобразование HealthGroupsEntity в HealthGroupDTO.
     *
     * @param entity сущность медицинской группы
     * @return DTO медицинской группы
     */
    public static HealthGroupDTO map(HealthGroupsEntity entity) {
        if (entity == null) {
            return null;
        }

        return HealthGroupDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .build();
    }

    /**
     * Преобразование SectionEntity в SectionDTO.
     *
     * @param entity сущность секции
     * @return DTO секции
     */
    public static SectionDTO map(SectionEntity entity) {
        if (entity == null) {
            return null;
        }

        return SectionDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .build();
    }

    /**
     * Преобразование DisciplineEntity в DisciplineDTO.
     *
     * @param entity сущность дисциплины
     * @return DTO дисциплины
     */
    public static DisciplineDTO map(DisciplineEntity entity) {
        if (entity == null) {
            return null;
        }

        return DisciplineDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    /**
     * Преобразование LessonsEntity в LessonDTO.
     *
     * @param entity сущность занятия
     * @return DTO занятия
     */
    public static LessonDTO map(LessonsEntity entity) {
        if (entity == null) {
            return null;
        }

        return LessonDTO.builder()
                .id(entity.getId())
                .disciplineId(entity.getDiscipline().getId())
                .disciplineName(entity.getDiscipline().getName())
                .dateOfLesson(entity.getDateOfLesson())
                .teacherId(entity.getTeacher() != null ? entity.getTeacher().getId() : null)
                .teacherFsp(entity.getTeacher() != null
                        ? entity.getTeacher().getFullName().getLastName() + " " +
                          entity.getTeacher().getFullName().getFirstName() + " " +
                          entity.getTeacher().getFullName().getPatronymic()
                        : null)
                .build();
    }

    /**
     * Преобразование VisitsEntity в VisitDTO.
     *
     * @param entity сущность посещения
     * @return DTO посещения
     */
    public static VisitDTO map(VisitsEntity entity) {
        if (entity == null) {
            return null;
        }

        return VisitDTO.builder()
                .id(entity.getId())
                .studentId(entity.getStudent().getId())
                .studentLogin(entity.getStudent().getLogin())
                .lessonId(entity.getLessons().getId())
                .isExists(entity.isExists())
                .build();
    }

    /**
     * Преобразование TeacherEntity в UserDetailsImpl.
     *
     * @param entity сущность преподавателя
     * @return данные пользователя для безопасности
     */
    public static UserDetailsImpl mapUserDetails(TeacherEntity entity) {
        if (entity == null) {
            return null;
        }

        String role = entity.isModerator() ? "moderator" : "teacher";
        return UserDetailsImpl.builder()
                .id(entity.getId())
                .firstName(entity.getFullName().getFirstName())
                .lastName(entity.getFullName().getLastName())
                .patronymic(entity.getFullName().getPatronymic())
                .login(entity.getLogin())
                .passwordHash(entity.getPasswordHash())
                .role(role)
                .build();
    }

    /**
     * Преобразование StudentEntity в UserDetailsImpl.
     *
     * @param entity сущность студента
     * @return данные пользователя для безопасности
     */
    public static UserDetailsImpl mapUserDetails(StudentEntity entity) {
        if (entity == null) {
            return null;
        }

        return UserDetailsImpl.builder()
                .id(entity.getId())
                .firstName(entity.getFullName().getFirstName())
                .lastName(entity.getFullName().getLastName())
                .patronymic(entity.getFullName().getPatronymic())
                .login(entity.getLogin())
                .passwordHash(entity.getPasswordHash())
                .role("student")
                .build();
    }
}
