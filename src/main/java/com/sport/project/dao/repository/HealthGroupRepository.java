package com.sport.project.dao.repository;

import com.sport.project.dao.entity.HealthGroupsEntity;
import com.sport.project.dao.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface HealthGroupRepository extends JpaRepository<HealthGroupsEntity, Integer> {

    //Поиск группы здоровья по ее id
    Optional<HealthGroupsEntity> findById(Integer id);

    //Поиск группы здоровья по ее названию
    Optional<HealthGroupsEntity> findByName(String name);

    //Поиск всех студентов по id группы здоровья
    @Query("SELECT s FROM student_entity s WHERE s.healthGroup.id = :healthGroup_id")
    List<StudentEntity> findStudentsByHealthGroupId(@Param("healthGroup_id") Integer healthGroup_id);

    //Поиск всех всех студентов по названию группы здоровья
    @Query("SELECT s FROM student_entity s WHERE s.healthGroup.name = :healthGroup_name")
    List<StudentEntity> findStudentsByHealthGroupName(@Param("healthGroup_name") String healthGroup_name);

    //Проверка существоания группы здорвья по названию
    boolean existsByName(String name);

    //Удаление группы здоровья по имени
    @Modifying
    @Query("DELETE FROM health_groups_entity h WHERE h.name = :name")
    void deleteByName(@Param("name") String name);
}
