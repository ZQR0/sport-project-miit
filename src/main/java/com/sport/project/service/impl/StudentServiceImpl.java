package com.sport.project.service.impl;

import com.sport.project.dao.entity.StudentEntity;
import com.sport.project.dao.entity.TeacherEntity;
import com.sport.project.dao.repository.impl.StudentRepositoryImpl;
import com.sport.project.dao.repository.impl.TeacherRepositoryImpl;
import com.sport.project.dto.StudentCreationDTO;
import com.sport.project.dto.StudentDTO;
import com.sport.project.exception.EntityAlreadyExistsException;
import com.sport.project.exception.EntityNotFoundException;
import com.sport.project.mapper.Mapper;
import com.sport.project.service.StudentService;
import com.sport.project.service.interfaces.student.StudentBusiness;
import com.sport.project.service.interfaces.student.StudentCreationService;
import com.sport.project.service.interfaces.student.StudentDeletingService;
import com.sport.project.service.interfaces.student.StudentUpdatingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService, StudentBusiness, StudentUpdatingService,
        StudentCreationService, StudentDeletingService {

    private final StudentRepositoryImpl repository;
    private final PasswordEncoder passwordEncoder;
    private final TeacherRepositoryImpl teacherRepository;

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
    public Map<Date, String> getStudentSchedule(StudentEntity student) {
        return student.getTeacher().getSchedule();
    }



    @Override
    public void deleteById(int id) throws EntityNotFoundException {
        log.info("Not available to use this method now [deleteById]");
    }

    @Override
    public void deleteByFSP(String fsp) throws EntityNotFoundException {
        log.info("Not available to use this method now [deleteByFSP]");
    }

    @Override
    public void deleteByLogin(String login) throws EntityNotFoundException {
        log.info("Not available to use this method now [deleteByLogin]");
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public void updateFSP(String newFSP, String login) throws EntityNotFoundException {
        StudentEntity entity = this.repository.findByLogin(login)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Student with login %s not found", login)));

        entity.setFsp(newFSP);
        this.repository.save(entity);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public void updateLogin(String newLogin, String login) throws EntityNotFoundException {
        StudentEntity entity = this.repository.findByLogin(login)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Student with login %s not found", login)));

        entity.setLogin(newLogin);
        this.repository.save(entity);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public void updateHealthGroup(int newHealthGroup, String login) throws EntityNotFoundException {
        StudentEntity entity = this.repository.findByLogin(login)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Student with login %s not found", login)));

        entity.setHealthGroup(newHealthGroup);
        this.repository.save(entity);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public void updateStudentTeacher(TeacherEntity teacher, String login) throws EntityNotFoundException {
        StudentEntity entity = this.repository.findByLogin(login)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Student with login %s not found", login)));
        entity.setTeacher(teacher);

        this.repository.save(entity);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public StudentDTO createStudent(StudentCreationDTO dto) throws EntityAlreadyExistsException {
        Map<Date, Boolean> exist = new HashMap<>();

        String login = dto.getLogin();
        Optional<StudentEntity> optionalStudentEntity = this.repository.findByLogin(login);
        if (optionalStudentEntity.isPresent()) throw new EntityAlreadyExistsException(String.format("StudentEntity with login %s already exists", login));

        TeacherEntity teacher;
        try {
            teacher = findTeacherById(dto.getTeacherId());
        } catch (EntityNotFoundException ex) {
            log.error(ex.getMessage());
            return null;
        }


        StudentEntity entity = StudentEntity.builder()
                .fsp(dto.getFsp())
                .login(login)
                .passwordHash(this.passwordEncoder.encode(dto.getPassword()))
                .healthGroup(dto.getHealthGroup())
                .exist(exist)
                .teacher(teacher)
                .build();

        return Mapper.map(entity);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    private TeacherEntity findTeacherById(int id) throws EntityNotFoundException {
        TeacherEntity teacher = this.teacherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("TeacherEntity with id %s not found", id)));
        return teacher;
    }


}
