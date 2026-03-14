package com.sport.project.dao.repository;

import com.sport.project.dao.entity.StudentEntity;

import java.util.Optional;

public interface StudentRepository {
    Optional<StudentEntity> findByFSP(String fsp);
    Optional<StudentEntity> findByLogin(String login);

    /*Поиск по группе. Найти всех студентов группы.
    * Это отсебятина немного, может удалишь*/
    Optional<StudentEntity> findByGroupName(String groupName);
}
