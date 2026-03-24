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

/**
 * Утилитарный класс для преобразования сущностей в DTO и обратно.
 */
public class Mapper {

    /**
     * Преобразование StudentEntity в StudentDTO.
     *
     * @param entity сущность студента
     * @return DTO студента
     */
//    public static StudentDTO map(StudentEntity entity) {
//        return StudentDTO.builder()
//                .id(entity.getId())
//                .fsp(entity.getFullName().getLastName() + " " +
//                     entity.getFullName().getFirstName() + " " +
//                     entity.getFullName().getPatronymic())
//                .login(entity.getLogin())
//                .passwordHash(entity.getPasswordHash())
//                .healthGroup(entity.getHealthGroup().getId())
//                .exist(entity.getExist())
//                .teacherId(entity.getTeacher().getId())
//                .build();
//    }
//
//    /**
//     * Преобразование TeacherEntity в TeacherDTO.
//     *
//     * @param entity сущность преподавателя
//     * @return DTO преподавателя
//     */
//    public static TeacherDTO map(TeacherEntity entity) {
//        return TeacherDTO.builder()
//                .id(entity.getId())
//                .fsp(entity.getFullName().getLastName() + " " +
//                     entity.getFullName().getFirstName() + " " +
//                     entity.getFullName().getPatronymic())
//                .login(entity.getLogin())
//                .passwordHash(entity.getPasswordHash())
//                .isModerator(entity.isModerator())
//                .schedule(entity.getSchedule())
//                .students(entity.getStudents())
//                .build();
//    }
//
//    /**
//     * Преобразование GroupEntity в GroupDTO.
//     *
//     * @param entity сущность группы
//     * @return DTO группы
//     */
//    public static GroupDTO map(GroupEntity entity) {
//        return GroupDTO.builder()
//                .id(entity.getId())
//                .name(entity.getName())
//                .institute(entity.getInstitute())
//                .build();
//    }
//
//    /**
//     * Преобразование HealthGroupsEntity в HealthGroupDTO.
//     *
//     * @param entity сущность медицинской группы
//     * @return DTO медицинской группы
//     */
//    public static HealthGroupDTO map(HealthGroupsEntity entity) {
//        return HealthGroupDTO.builder()
//                .id(entity.getId())
//                .name(entity.getName())
//                .description(entity.getDescription())
//                .build();
//    }
//
//    /**
//     * Преобразование SectionEntity в SectionDTO.
//     *
//     * @param entity сущность секции
//     * @return DTO секции
//     */
//    public static SectionDTO map(SectionEntity entity) {
//        return SectionDTO.builder()
//                .id(entity.getId())
//                .name(entity.getName())
//                .description(entity.getDescription())
//                .build();
//    }
//
//    /**
//     * Преобразование DisciplineEntity в DisciplineDTO.
//     *
//     * @param entity сущность дисциплины
//     * @return DTO дисциплины
//     */
//    public static DisciplineDTO map(DisciplineEntity entity) {
//        return DisciplineDTO.builder()
//                .id(entity.getId())
//                .name(entity.getName())
//                .build();
//    }
//
//    /**
//     * Преобразование LessonsEntity в LessonDTO.
//     *
//     * @param entity сущность занятия
//     * @return DTO занятия
//     */
//    public static LessonDTO map(LessonsEntity entity) {
//        return LessonDTO.builder()
//                .id(entity.getId())
//                .disciplineId(entity.getDiscipline().getId())
//                .disciplineName(entity.getDiscipline().getName())
//                .dateOfLesson(entity.getDateOfLesson())
//                .teacherId(entity.getTeacher() != null ? entity.getTeacher().getId() : null)
//                .teacherFsp(entity.getTeacher() != null ?
//                        entity.getTeacher().getFullName().getLastName() + " " +
//                        entity.getTeacher().getFullName().getFirstName() + " " +
//                        entity.getTeacher().getFullName().getPatronymic() : null)
//                .build();
//    }
//
//    /**
//     * Преобразование VisitsEntity в VisitDTO.
//     *
//     * @param entity сущность посещения
//     * @return DTO посещения
//     */
//    public static VisitDTO map(VisitsEntity entity) {
//        return VisitDTO.builder()
//                .id(entity.getId())
//                .studentId(entity.getStudent().getId())
//                .studentLogin(entity.getStudent().getLogin())
//                .lessonId(entity.getLessons().getId())
//                .isExists(entity.isExists())
//                .build();
//    }
//
//    /**
//     * Преобразование TeacherEntity в UserDetailsImpl.
//     *
//     * @param entity сущность преподавателя
//     * @return данные пользователя для безопасности
//     */
//    public static UserDetailsImpl mapUserDetails(TeacherEntity entity) {
//        String role = entity.isModerator() ? "moderator" : "teacher";
//        return UserDetailsImpl.builder()
//                .id(entity.getId())
//                .fsp(entity.getFullName().getLastName() + " " +
//                     entity.getFullName().getFirstName() + " " +
//                     entity.getFullName().getPatronymic())
//                .login(entity.getLogin())
//                .passwordHash(entity.getPasswordHash())
//                .role(role)
//                .build();
//    }
//
//    /**
//     * Преобразование StudentEntity в UserDetailsImpl.
//     *
//     * @param entity сущность студента
//     * @return данные пользователя для безопасности
//     */
//    public static UserDetailsImpl mapUserDetails(StudentEntity entity) {
//        return UserDetailsImpl.builder()
//                .id(entity.getId())
//                .fsp(entity.getFullName().getLastName() + " " +
//                     entity.getFullName().getFirstName() + " " +
//                     entity.getFullName().getPatronymic())
//                .login(entity.getLogin())
//                .passwordHash(entity.getPasswordHash())
//                .role("student")
//                .build();
//    }
}
