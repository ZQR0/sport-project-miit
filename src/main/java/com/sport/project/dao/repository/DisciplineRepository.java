package com.sport.project.dao.repository;


import com.sport.project.dao.entity.DisciplineEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DisciplineRepository extends JpaRepository<DisciplineEntity, Long> {

    /*Не добавила поиск по айди, потому что вроде как все основные методы в
    абстрактном интерфейсе
     */

    //Поиск дисциплины по имени
    Optional<DisciplineEntity> findByName(String name);

}
