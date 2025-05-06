package com.sport.project.service.impl;

import com.sport.project.dao.entity.StudentEntity;
import com.sport.project.dao.entity.TeacherEntity;
import com.sport.project.dao.repository.impl.StudentRepositoryImpl;
import com.sport.project.dto.StudentCreationDTO;
import com.sport.project.dto.StudentDTO;
import com.sport.project.exception.EntityAlreadyExistsException;
import com.sport.project.exception.EntityNotFoundException;
import com.sport.project.mapper.Mapper;
import com.sport.project.service.StudentService;
import com.sport.project.service.interfaces.StudentBusiness;
import com.sport.project.service.interfaces.StudentCreationService;
import com.sport.project.service.interfaces.StudentDeletingService;
import com.sport.project.service.interfaces.StudentUpdatingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService, StudentBusiness, StudentUpdatingService,
        StudentCreationService, StudentDeletingService {

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

    @Override
    public Map<Date, String> getStudentSchedule(StudentEntity student) {
        return Map.of();
    }

    @Override
    public StudentDTO createStudentByModerator(StudentCreationDTO dto, TeacherEntity moderator) throws EntityAlreadyExistsException {
        return null;
    }

    @Override
    public StudentDTO createStudent(StudentCreationDTO dto) throws EntityAlreadyExistsException {
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
    public boolean updateFSP(String newFSP, StudentEntity entity) throws EntityNotFoundException {
        return false;
    }

    @Override
    public boolean updateLogin(String newLogin, StudentEntity entity) throws EntityNotFoundException {
        return false;
    }

    @Override
    public boolean updateHealthGroup(int newHealthGroup, StudentEntity entity) throws EntityNotFoundException {
        return false;
    }

    @Override
    public boolean updateStudentTeacher(int newTeacherId, StudentEntity entity) throws EntityNotFoundException {
        return false;
    }
}
