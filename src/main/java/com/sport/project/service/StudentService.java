package com.sport.project.service;

import com.sport.project.dao.entity.StudentEntity;
import com.sport.project.dto.StudentDTO;
import com.sport.project.exception.EntityNotFoundException;

import java.util.List;

public interface StudentService {
    StudentDTO findById(Integer id) throws EntityNotFoundException;
    StudentDTO findByLogin(String login) throws EntityNotFoundException;
    StudentDTO findByFSP(String fsp) throws EntityNotFoundException;
    List<StudentDTO> findAll();
    StudentDTO create(StudentEntity entity);
}
