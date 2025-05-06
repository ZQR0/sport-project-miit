package com.sport.project.service.impl;

import com.sport.project.dao.entity.StudentEntity;
import com.sport.project.dao.entity.TeacherEntity;
import com.sport.project.dao.repository.impl.TeacherRepositoryImpl;
import com.sport.project.dto.TeacherCreationDTO;
import com.sport.project.dto.TeacherDTO;
import com.sport.project.exception.EntityAlreadyExistsException;
import com.sport.project.exception.EntityNotFoundException;
import com.sport.project.mapper.Mapper;
import com.sport.project.service.TeacherService;
import com.sport.project.service.interfaces.TeacherBusiness;
import com.sport.project.service.interfaces.TeacherCreationService;
import com.sport.project.service.interfaces.TeacherDeletingService;
import com.sport.project.service.interfaces.TeacherUpdatingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeacherServiceImpl implements TeacherService, TeacherCreationService, TeacherUpdatingService, TeacherDeletingService, TeacherBusiness {

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

    @Override
    public Map<Date, String> updateSchedule(Map<Date, String> newSchedule) {
        return Map.of();
    }

    @Override
    public boolean noticeStudent(StudentEntity student) throws EntityNotFoundException {
        return false;
    }

    @Override
    public TeacherDTO createTeacher(TeacherCreationDTO dto) throws EntityAlreadyExistsException {
        return null;
    }

    @Override
    public void deleteById(int id) throws EntityNotFoundException {

    }

    @Override
    public void deleteByFSP(String fsp) throws EntityNotFoundException {

    }

    @Override
    public void deleteByLogin(String login) throws EntityNotFoundException {

    }

    @Override
    public void updateFSP(String newFSP, TeacherEntity entity) throws EntityNotFoundException {

    }

    @Override
    public void updateLogin(String newLogin, TeacherEntity entity) throws EntityNotFoundException {

    }

    @Override
    public void updateIsModerator(boolean isModerator, TeacherEntity entity) throws EntityNotFoundException {

    }
}
