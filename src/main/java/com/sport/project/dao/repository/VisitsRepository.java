package com.sport.project.dao.repository;

import com.sport.project.dao.entity.VisitsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface VisitsRepository extends JpaRepository<VisitsEntity, Integer> {

    /*Поиск посещений студента по id студента*/
    List<VisitsEntity> findByStudentId(Integer studentId);

    /*Поиск посещений студента по логину студента*/
    @Query("SELECT v FROM visits_entity v WHERE v.student.login = :studentLogin")
    List<VisitsEntity> findByStudentLogin(String studentLogin);

    /**
     * Метод для получения посещения по данным студента и занятия
     * @param lessonId Id посещения
     * @param studentLogin логин студента
     * */
    @Query("SELECT v FROM visits_entity v " +
            "WHERE v.student.login = :studentLogin AND v.lessons.id = :lessonId")
    Optional<VisitsEntity> findByStudentIdAndLessonId(String studentLogin, Integer lessonId);

    /**
     * Метод для получения посещения по Id занятия
     * @param lessonId Id занятия
     * */
    @Query("SELECT v FROM visits_entity v WHERE v.lessons.id = :lessonId")
    List<VisitsEntity> findByLessonId(Integer lessonId);

    //Удаление посещений по логину студента
    void deleteByStudent_Login(String studentLogin);

    @Query("SELECT v FROM visits_entity v WHERE v.lessons.dateOfLesson BETWEEN :from AND :to")
    List<VisitsEntity> findByDateRange(LocalDate from, LocalDate to);
}
