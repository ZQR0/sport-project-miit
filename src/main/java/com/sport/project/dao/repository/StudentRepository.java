package com.sport.project.dao.repository;

import com.sport.project.dao.entity.StudentEntity;

import java.util.Optional;

public interface StudentRepository {
    Optional<StudentEntity> findByFSP(String fsp);
    Optional<StudentEntity> findByLogin(String login);
}
