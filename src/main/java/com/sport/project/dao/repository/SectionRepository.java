package com.sport.project.dao.repository;

import com.sport.project.dao.entity.SectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SectionRepository extends JpaRepository<SectionEntity, Integer> {

    //Поиск по названию секции
    Optional<SectionEntity> findByName(String name);

    //Существование секции по названию
    boolean existsByName(String name);
}
