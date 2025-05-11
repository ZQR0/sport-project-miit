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
import com.sport.project.service.interfaces.teacher.TeacherBusiness;
import com.sport.project.service.interfaces.teacher.TeacherCreationService;
import com.sport.project.service.interfaces.teacher.TeacherDeletingService;
import com.sport.project.service.interfaces.teacher.TeacherUpdatingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeacherServiceImpl implements TeacherService, TeacherCreationService,
        TeacherUpdatingService,
        TeacherDeletingService,
        TeacherBusiness {

    private final TeacherRepositoryImpl repository;
    private final PasswordEncoder passwordEncoder;

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
    public Map<LocalDate, String> updateSchedule(LocalDate date, String lessonName, TeacherEntity entity) {
        Map<LocalDate, String> schedule = entity.getSchedule();
        schedule.putIfAbsent(date, lessonName);

        entity.setSchedule(schedule);
        this.repository.save(entity);
        return schedule;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public boolean noticeStudent(@NonNull StudentEntity student, @NonNull LocalDate date) throws EntityNotFoundException {
        Map<LocalDate, Boolean> existMap = student.getExist();
        existMap.putIfAbsent(date, true);

        return existMap.containsKey(date);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public TeacherDTO createTeacher(@NonNull TeacherCreationDTO dto) throws EntityAlreadyExistsException {

        Optional<TeacherEntity> optionalTeacherEntity = this.repository.findByLogin(dto.getLogin());
        if (optionalTeacherEntity.isPresent()) throw new EntityAlreadyExistsException(String.format("Teacher with login %s already exists"));

        Map<LocalDate, String> emptySchedule = new HashMap<>();
        TeacherEntity entity = TeacherEntity.builder()
                .fsp(dto.getFsp())
                .login(dto.getLogin())
                .passwordHash(this.passwordEncoder.encode(dto.getPassword()))
                .isModerator(false)
                .schedule(emptySchedule)
                .build();

        TeacherEntity saved = this.repository.save(entity);
        return Mapper.map(saved);
    }

    @Override
    public void deleteById(int id) throws EntityNotFoundException {
        log.info("No available to use this method (ID)");
    }

    @Override
    public void deleteByFSP(String fsp) throws EntityNotFoundException {
        log.info("No available to use this method (FSP)");
    }

    @Override
    public void deleteByLogin(String login) throws EntityNotFoundException {
        log.info("No available to use this method (LOGIN)");
    }


    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public void updateFSP(String newFSP, String login) throws EntityNotFoundException {
        TeacherEntity entity = this.repository.findByFSP(login)
                .orElseThrow(() -> new EntityNotFoundException(String.format("TeacherEntity with login %s not found")));

        entity.setFsp(newFSP);
        this.repository.save(entity);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public void updateLogin(String newLogin, String login) throws EntityNotFoundException {
        TeacherEntity entity = this.repository.findByLogin(login)
                .orElseThrow(() -> new EntityNotFoundException(String.format("TeacherEntity with login %s not found", login)));

        entity.setLogin(newLogin);
        this.repository.save(entity);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public void updateIsModerator(boolean isModerator, int id) throws EntityNotFoundException {
        TeacherEntity entity = this.repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("TeacherEntity with id %s not found", id)));

        entity.setModerator(isModerator);
        this.repository.save(entity);
    }
}
