package com.sport.project.dao.repository;

import com.sport.project.dao.entity.LessonsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

//В документации нет, но может еще понадобится
public interface LessonsRepository extends JpaRepository<LessonsEntity, Integer> {

    //Поиск занятий по id дисциплин
    @Query("SELECT l FROM lessons_entity l WHERE l.discipline.id = :discipline_id")
    List<LessonsEntity> findByDisciplineId(@Param("discipline_id") Integer disciplineId);

    //Получение расписание занятий по дисциплине disciplineName
    List<LessonsEntity> findByDiscipline_Name(String disciplineName);

    //Получение расписание преподавателя, список всех занятий, которые проводит преподаватель
    @Query("""
            SELECT DISTINCT
                discipline.name as disciplineName,
                lesson.dateOfLesson,
                group.name as groupName
            FROM TeacherEntity t
            JOIN FETCH t.lessons lesson
            JOIN FETCH lesson.disciplines discipline
            JOIN FETCH lesson.visits visit
            JOIN FETCH visit.students student
            JOIN FETCH student.group group
            WHERE t.login = :teacherLogin
            """)
    List<LessonsEntity> findByTeacher_Login(@Param("teacherLogin") String teacherLogin);

    //Поиск занятий по id учителя
    @Query("SELECT l FROM lessons_entity l WHERE l.teacher.id = :teacher_id")
    List<LessonsEntity> findByTeacherId(@Param("teacher_id") Integer teacher_id);

    //Список занятий на указанную дату
    List<LessonsEntity> findByDateOfLesson(LocalDate date);

    //Поиск занятий по дисциплине и дате
    @Query("SELECT l FROM lessons_entity l WHERE l.discipline.id = :disciplineId AND l.dateOfLesson = :date")
    Optional<LessonsEntity> findByDisciplineAndDate(@Param("disciplineId") Integer disciplineId,
                                                    @Param("date") LocalDate date);

    //Поиск занятий в диапазоне дат
    @Query("SELECT l FROM lessons_entity l WHERE l.dateOfLesson BETWEEN :from AND :to")
    List<LessonsEntity> findByDateRange(@Param("from") LocalDate from, @Param("to") LocalDate to);

    //Очистка занятий при удалении дисциплины
    void deleteByDiscipline_Name(String disciplineName);
}
