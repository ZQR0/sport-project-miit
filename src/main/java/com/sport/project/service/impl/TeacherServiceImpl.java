package com.sport.project.service.impl;

import com.sport.project.dao.entity.TeacherEntity;
import com.sport.project.dao.repository.impl.TeacherRepositoryImpl;
import com.sport.project.dto.TeacherDTO;
import com.sport.project.exception.EntityNotFoundException;
import com.sport.project.mapper.Mapper;
import com.sport.project.service.TeacherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepositoryImpl repository;

    @Override
    public TeacherDTO findById(Integer id) throws EntityNotFoundException {
        if (id == null) {
            log.warn("Null id provided, error");
            throw new EntityNotFoundException("Null id provided");
        }

        TeacherEntity teacherEntity = this.repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Teacher entity with id %s not found", id)));

        return Mapper.map(teacherEntity);
    }

    @Override
    public TeacherDTO findByLogin(String login) throws EntityNotFoundException {
        if (login == null) throw new EntityNotFoundException("Null login provided");

        TeacherEntity teacherEntity = this.repository.findByLogin(login)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Teacher entity with login %s not found", login)));

        return Mapper.map(teacherEntity);
    }

    @Override
    public TeacherDTO findByFSP(String fsp) throws EntityNotFoundException {
        if (fsp == null) throw new EntityNotFoundException("Null fsp provided");

        TeacherEntity teacherEntity = this.repository.findByFSP(fsp)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Teacher entity with FSP %s not found", fsp)));

        return Mapper.map(teacherEntity);
    }

    @Override
    public List<TeacherDTO> findAllModerators() {
        return this.repository.findAllModerators()
                .stream()
                .map(Mapper::map)
                .toList();
    }

    @Override
    public List<TeacherDTO> findAll() {
        return this.repository.findAll()
                .stream()
                .map(Mapper::map)
                .toList();
    }

    @Override
    public TeacherDTO create(TeacherEntity entity) {
        TeacherEntity teacherEntity = this.repository.save(entity);
        return Mapper.map(teacherEntity);
    }
}
