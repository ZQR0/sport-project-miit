package com.sport.project.dao.repository;

import com.sport.project.dao.entity.LessonsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

//В документации нет, но может еще понадобится
public interface LessonsRepository extends JpaRepository<LessonsEntity, Integer> {

    //Получение расписание занятий по дисциплине disciplineName
    List<LessonsEntity> findByDiscipline_Name(String disciplineName);

    //Получение расписание преподавателя, список всех занятий, которые проводит преподаватель
    @Query("""
            SELECT DISTINCT
                discipline.name as disciplineName,
                lesson.dateOfLesson,
                group.name as groupName
            FROM TeacherEntity t
            JOIN t.lessons lesson
            JOIN lesson.disciplines discipline
            JOIN lesson.visits visit
            JOIN visit.students student
            JOIN student.group group
            WHERE t.login = :teacherLogin
            """)
    List<LessonsEntity> findByTeacher_Login(@Param("teacherLogin") String teacherLogin);

    //Список занятий на указанную дату
    List<LessonsEntity> findByDateOfLesson(LocalDate date);

    //Очистка занятий при удалении дисциплины
    void deleteByDiscipline_Name(String disciplineName);
}
