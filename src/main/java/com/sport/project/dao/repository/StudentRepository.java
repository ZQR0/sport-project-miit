package com.sport.project.dao.repository;

import com.sport.project.dao.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<StudentEntity, Integer> {

    //Поиск студента по логину
    Optional<StudentEntity> findByLogin(String login);

    //Поиск студента по LFP (ФИО, с заменой "_" на пробел)
    @Query("SELECT student FROM student_entity student" +
            "WHERE CONCAT(student.fullName.lastName, ' ', student.fullName.firstName, ' ', COALESCE(student.fullName.patronymic))" +
            "LIKE CONCAT('%', :lfp, '%')")
    List<StudentEntity> findByLFP(@Param("lfp") String lfp);

    //Поиск всех студентов
    List<StudentEntity> findAll();

    /*Поиск по группе. Найти всех студентов группы.
    * Это отсебятина немного, может удалишь*/
    Optional<StudentEntity> findByGroupName(String groupName);
}