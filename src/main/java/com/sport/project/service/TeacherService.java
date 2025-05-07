package com.sport.project.service;

import com.sport.project.dao.entity.TeacherEntity;
import com.sport.project.dto.TeacherDTO;
import com.sport.project.exception.EntityNotFoundException;

import java.util.List;

public interface TeacherService {
    TeacherDTO findById(Integer id) throws EntityNotFoundException;
    TeacherDTO findByLogin(String login) throws EntityNotFoundException;
    TeacherDTO findByFSP(String fsp) throws EntityNotFoundException;
    List<TeacherDTO> findAllModerators();
    List<TeacherDTO> findAll();
}