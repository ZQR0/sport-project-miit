package com.sport.project.dao.repository;


import com.sport.project.dao.entity.DisciplineEntity;
import com.sport.project.dao.entity.LessonsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DisciplineRepository extends JpaRepository<DisciplineEntity, Integer> {

    /*Не добавила поиск по айди, потому что вроде как все основные методы в
    абстрактном интерфейсе
     */

    //

    //Поиск дисциплины по имени
    Optional<DisciplineEntity> findByName(String name);

    //Проверка существования дисциплины по названию
    boolean existsByName(String name);

    // Получение занятий по айди дисциплины в заданный промежуток дат
    @Query("SELECT l FROM lessons_entity l WHERE l.discipline.id = :disciplineId AND l.dateOfLesson BETWEEN :from AND :to")
    List<LessonsEntity> getLessonsByDateRange(@Param("disciplineId") Integer disciplineId, @Param("from") LocalDate from, @Param("to") LocalDate to);

    // Проверка возможности удаления дисциплины (true - можно удалять, false - нельзя)
    @Query("SELECT count(l) = 0 FROM lessons_entity l WHERE l.discipline.id = :disciplineId")
    boolean canDelete(@Param("disciplineId") Integer disciplineId);

    // Удаление дисциплины по ее названию
    @Modifying
    @Query("DELETE FROM discipline_entity d WHERE d.name = :disciplineName")
    void deleteByName(@Param("disciplineName") String disciplineName);
}
