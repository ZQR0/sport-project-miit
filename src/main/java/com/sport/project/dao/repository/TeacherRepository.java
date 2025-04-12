package com.sport.project.dao.repository;

import com.sport.project.dao.entity.TeacherEntity;

import java.util.List;
import java.util.Optional;

public interface TeacherRepository {
    Optional<TeacherEntity> findByFSP(String fsp);
    Optional<TeacherEntity> findByLogin(String login);
    List<TeacherEntity> findAllModerators();
}
