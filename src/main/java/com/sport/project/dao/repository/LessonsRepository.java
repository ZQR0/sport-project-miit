package com.sport.project.dao.repository;

import com.sport.project.dao.entity.LessonsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

//В документации нет, но может еще понадобится
public interface LessonsRepository extends JpaRepository<LessonsEntity, Long> {

    //Получение расписание занятий по дисциплине disciplineName
    List<LessonsEntity> findByDiscipline_Name(String disciplineName);

    //Получение расписание преподавателя, список всех занятий, которые проводит преподаватель
    List<LessonsEntity> findByTeacher_Login(String teacherLogin);

    //Список занятий на указанную дату
    List<LessonsEntity> findByDateOfLesson(LocalDate date);
}
