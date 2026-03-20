package com.sport.project.dao.repository;

import com.sport.project.dao.entity.DisciplineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DisciplineRepository extends JpaRepository<DisciplineEntity, Long> {

    //Все занятия данной секции
    @Query("""
            SELECT DISTINCT d
            FROM discipline_entity d
            JOIN d.lessonsEntities l
            WHERE l.name = :lessonName
            """)
    List<DisciplineEntity> findByLessonName(@Param("lessonName") String lessonName);

    //Поиск дисциплины по имени
    Optional<DisciplineEntity> findByName(String name);

}
