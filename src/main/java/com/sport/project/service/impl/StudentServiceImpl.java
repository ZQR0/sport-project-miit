package com.sport.project.service.impl;

import com.sport.project.dao.entity.StudentEntity;
import com.sport.project.dao.repository.impl.StudentRepositoryImpl;
import com.sport.project.dto.StudentDTO;
import com.sport.project.exception.EntityNotFoundException;
import com.sport.project.mapper.Mapper;
import com.sport.project.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService {

    private final StudentRepositoryImpl repository;

    @Override
    public StudentDTO findById(Integer id) throws EntityNotFoundException {
        if (id == null) throw new EntityNotFoundException("Null id provided");

        StudentEntity studentEntity = this.repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Teacher entity with id %s not found", id)));

        return Mapper.map(studentEntity);
    }

    @Override
    public StudentDTO findByLogin(String login) throws EntityNotFoundException {
        if (login == null) throw new EntityNotFoundException("Null login provided");

        StudentEntity studentEntity = this.repository.findByLogin(login)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Student entity with login %s not found")));

        return Mapper.map(studentEntity);
    }

    @Override
    public StudentDTO findByFSP(String fsp) throws EntityNotFoundException {
        if (fsp == null) throw new EntityNotFoundException("Null fsp provided");

        StudentEntity studentEntity = this.repository.findByFSP(fsp)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Student entity with FSP %s not found", fsp)));

        return Mapper.map(studentEntity);
    }

    @Override
    public List<StudentDTO> findAll() {
        return this.repository.findAll()
                .stream()
                .map(Mapper::map)
                .toList();
    }

    @Override
    public StudentDTO create(StudentEntity entity) {
        StudentEntity studentEntity = this.repository.save(entity);
        return Mapper.map(studentEntity);
    }
}
