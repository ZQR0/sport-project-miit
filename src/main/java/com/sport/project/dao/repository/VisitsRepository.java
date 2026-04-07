package com.sport.project.dao.repository;

import com.sport.project.dao.entity.VisitsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface VisitsRepository extends JpaRepository<VisitsEntity, Integer> {

    @Query("SELECT v FROM visits_entity  v " +
            "JOIN FETCH v.lessons l " +
            "JOIN FETCH l.discipline " +
            "LEFT JOIN FETCH l.teacher " +
            "JOIN FETCH v.student s " +
            "JOIN FETCH s.group " +
            "JOIN FETCH s.healthGroup " +
            "LEFT JOIN FETCH s.section " +
            "ORDER BY v.id ASC")
    List<VisitsEntity> findAllOptimized();

    /*Поиск посещений студента по id студента*/
    List<VisitsEntity> findByStudentId(Integer studentId);

    /*Поиск посещений студента по логину студента*/
    @Query("SELECT v FROM visits_entity v WHERE v.student.login = :studentLogin")
    List<VisitsEntity> findByStudentLogin(@Param("studentLogin") String studentLogin);

    /**
     * Метод для получения посещения по данным студента и занятия
     * @param lessonId Id посещения
     * @param studentLogin логин студента
     * */
    @Query("SELECT v FROM visits_entity v " +
            "WHERE v.student.login = :studentLogin AND v.lessons.id = :lessonId")
    Optional<VisitsEntity> findByStudentIdAndLessonId(@Param("studentLogin") String studentLogin, @Param("lessonId") Integer lessonId);

    /**
     * Метод для получения посещения по Id занятия
     * @param lessonId Id занятия
     * */
    @Query("SELECT v FROM visits_entity v WHERE v.lessons.id = :lessonId")
    List<VisitsEntity> findByLessonId(@Param("lessonId") Integer lessonId);

    //Удаление посещений по логину студента
    void deleteByStudent_Login(@Param("studentLogin") String studentLogin);

    @Query("SELECT v FROM visits_entity v WHERE v.lessons.dateOfLesson BETWEEN :from AND :to")
    List<VisitsEntity> findByDateRange(@Param("from") LocalDate from, @Param("to") LocalDate to);

    @Modifying
    @Query("DELETE FROM visits_entity v WHERE v.lessons.id = :lessonId")
    void deleteByLessonId(@Param("lessonId") Integer lessonId);
}
