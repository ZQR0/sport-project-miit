package com.sport.project.dao.repository;

import com.sport.project.dao.entity.GroupEntity;
import com.sport.project.dao.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<GroupEntity, Integer> {

    //Поиск по названию группы
    Optional<GroupEntity> findByName(String name);

    //Поиск всех студентов группы
    @Query("SELECT student FROM student_entity student WHERE student.group.name = :group_name")
    List<StudentEntity> findStudentsByGroup(@Param("group_name") String group_name);

    //Поиск по названию института
    List<GroupEntity> findByInstitute(String institute);

    //Получение групп по институту с пагинацией
    List<GroupEntity> findByInstitute(String institute, Pageable pageable);

    //Проверка существования группы по названию
    boolean existsByName(String name);
}
