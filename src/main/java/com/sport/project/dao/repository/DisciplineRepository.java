package com.sport.project.dao.repository;


import com.sport.project.dao.entity.DisciplineEntity;
import com.sport.project.dao.entity.LessonsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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
}
