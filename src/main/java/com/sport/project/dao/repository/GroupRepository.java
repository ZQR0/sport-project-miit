package com.sport.project.dao.repository;

import com.sport.project.dao.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<GroupEntity, Long> {

    //Поиск по названию группы
    Optional<GroupEntity> findByName(String name);

    //Поиск по названию института
    Optional<GroupEntity> findByInstitute(String institute);
}
