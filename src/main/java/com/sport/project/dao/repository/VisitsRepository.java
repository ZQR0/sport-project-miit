package com.sport.project.dao.repository;

import com.sport.project.dao.entity.VisitsEntity;
import com.sport.project.dao.repository.projection.AttendanceProjection;
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
    //FIXME: починить ошибку N+1
    List<VisitsEntity> findByStudentId(Integer studentId);

    /*Поиск посещений студента по логину студента*/
    //FIXME: починить ошибку N+1
    @Query("SELECT v FROM visits_entity v WHERE v.student.login = :studentLogin")
    List<VisitsEntity> findByStudentLogin(@Param("studentLogin") String studentLogin);

    /**
     * Метод для получения посещения по данным студента и занятия
     * @param lessonId Id посещения
     * @param studentLogin логин студента
     * */
    //FIXME: починить ошибку N+1
    @Query("SELECT v FROM visits_entity v " +
            "WHERE v.student.login = :studentLogin AND v.lessons.id = :lessonId")
    Optional<VisitsEntity> findByStudentIdAndLessonId(@Param("studentLogin") String studentLogin, @Param("lessonId") Integer lessonId);

    /**
     * Метод для получения посещения по Id занятия
     * @param lessonId Id занятия
     * */
    //FIXME: починить ошибку N+1
    @Query("SELECT v FROM visits_entity v WHERE v.lessons.id = :lessonId")
    List<VisitsEntity> findByLessonId(@Param("lessonId") Integer lessonId);

    //FIXME: починить ошибку N+1
    //Удаление посещений по логину студента
    void deleteByStudent_Login(@Param("studentLogin") String studentLogin);

    //TODO: фиксануть N+1
    @Query("SELECT v FROM visits_entity v WHERE v.lessons.dateOfLesson BETWEEN :from AND :to")
    List<VisitsEntity> findByDateRange(@Param("from") LocalDate from, @Param("to") LocalDate to);

    @Modifying
    @Query("DELETE FROM visits_entity v WHERE v.lessons.id = :lessonId")
    void deleteByLessonId(@Param("lessonId") Integer lessonId);

    //Поиск посещений и их студентов по id занятия
    @Query("SELECT v FROM visits_entity v " +
            "JOIN FETCH v.student s " +
            "WHERE v.lessons.id = :lessonId")
    List<VisitsEntity> findByLessonIdWithStudent(@Param("lessonId") Integer lessonId);

    @Query("SELECT v.lessons.dateOfLesson AS lessonDate, " +
            "v.exists AS isExists, " +
            "v.lessons.startAt AS startAt, " +
            "v.lessons.endAt " +
            "FROM visits_entity v " +
            "WHERE v.student.login = :studentLogin")
    List<AttendanceProjection> findAttendanceByStudentLogin(@Param("studentLogin") String studentLogin);

    //Поиск посещений по занятию и логину студента
    @Query("SELECT v FROM visits_entity v " +
            "WHERE v.lessons.id = :lessonId AND v.student.login = :studentLogin")
    Optional<VisitsEntity> findByLessonAndStudentLogin(@Param("lessonId") Integer lessonId,
                                                   @Param("studentLogin") String studentLogin);

    //Подсчет всех посещений по id занятия
    int countByLessons_Id(Integer lessonId);
}
