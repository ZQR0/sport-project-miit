package com.sport.project.service.impl;

import com.sport.project.dao.entity.StudentEntity;
import com.sport.project.dao.entity.TeacherEntity;
import com.sport.project.dao.repository.impl.StudentRepositoryImpl;
import com.sport.project.dao.repository.impl.TeacherRepositoryImpl;
import com.sport.project.dto.TeacherCreationDTO;
import com.sport.project.dto.TeacherDTO;
import com.sport.project.dto.UserDetailsImpl;
import com.sport.project.exception.EntityAlreadyExistsException;
import com.sport.project.exception.EntityNotFoundException;
import com.sport.project.mapper.Mapper;
import com.sport.project.service.TeacherService;
import com.sport.project.service.interfaces.teacher.TeacherBusiness;
import com.sport.project.service.interfaces.teacher.TeacherCreationService;
import com.sport.project.service.interfaces.teacher.TeacherDeletingService;
import com.sport.project.service.interfaces.teacher.TeacherUpdatingService;
import com.sport.project.utils.AuthorizedUserUtils;
import com.sport.project.utils.UserUtils;
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
public class TeacherServiceImpl implements TeacherService,
        TeacherCreationService,
        TeacherBusiness {

    private final TeacherRepositoryImpl teacherRepository;
    private final StudentRepositoryImpl studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserUtils userUtils;

    @Override
    public TeacherDTO findById(Integer id) throws EntityNotFoundException {
        if (id == null) {
            log.warn("Null id provided, error");
            throw new EntityNotFoundException("Null id provided");
        }

        TeacherEntity teacherEntity = this.teacherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Teacher entity with id %s not found", id)));

        return Mapper.map(teacherEntity);
    }

    @Override
    public TeacherDTO findByLogin(String login) throws EntityNotFoundException {
        if (login == null) throw new EntityNotFoundException("Null login provided");

        TeacherEntity teacherEntity = this.teacherRepository.findByLogin(login)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Teacher entity with login %s not found", login)));

        return Mapper.map(teacherEntity);
    }

    @Override
    public TeacherDTO findByFSP(String fsp) throws EntityNotFoundException {
        if (fsp == null) throw new EntityNotFoundException("Null fsp provided");

        TeacherEntity teacherEntity = this.teacherRepository.findByFSP(fsp)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Teacher entity with FSP %s not found", fsp)));

        return Mapper.map(teacherEntity);
    }

    @Override
    public List<TeacherDTO> findAllModerators() {
        return this.teacherRepository.findAllModerators()
                .stream()
                .map(Mapper::map)
                .toList();
    }

    @Override
    public List<TeacherDTO> findAll() {
        return this.teacherRepository.findAll()
                .stream()
                .map(Mapper::map)
                .toList();
    }


    @Override
    public Map<LocalDate, String> updateSchedule(LocalDate date, String lessonName, TeacherEntity entity) {
        Map<LocalDate, String> schedule = entity.getSchedule();
        schedule.putIfAbsent(date, lessonName);

        entity.setSchedule(schedule);
        this.teacherRepository.save(entity);
        return schedule;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public boolean noticeStudent(String login) throws EntityNotFoundException {
        if (this.userUtils.isStudentExistsByLogin(login)) {
            StudentEntity student = this.studentRepository.findByLogin(login).get();

            UserDetailsImpl userDetails = AuthorizedUserUtils.getCurrentUser();
            if (userDetails != null) {
                if (userDetails.getRole().equals("teacher") && isStudentsTeacher(userDetails.getLogin(), login)) {
                    Map<LocalDate, Boolean> existMap = student.getExist();
                    existMap.putIfAbsent(LocalDate.now(), true);
                    return true;
                }
            }

        }

        return false;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public TeacherDTO createTeacher(@NonNull TeacherCreationDTO dto) throws EntityAlreadyExistsException {

        String login = dto.getLogin();
        Optional<TeacherEntity> optionalTeacherEntity = this.teacherRepository.findByLogin(login);

        if (this.userUtils.isTeacherExistsByLogin(login) || this.userUtils.isStudentExistsByLogin(login)) {
            throw new EntityAlreadyExistsException(String.format("User with login %s already exists", login));
        }

        Map<LocalDate, String> emptySchedule = new HashMap<>();
        TeacherEntity entity = TeacherEntity.builder()
                .fsp(dto.getFsp())
                .login(login)
                .passwordHash(this.passwordEncoder.encode(dto.getPassword()))
                .isModerator(dto.getIsModerator())
                .schedule(emptySchedule)
                .build();

        TeacherEntity saved = this.teacherRepository.save(entity);
        return Mapper.map(saved);
    }

    // Проверка на то, что студент действительно является учеником препода
    private boolean isStudentsTeacher(String teachersLogin, String studentsLogin) {
        TeacherEntity teacher = this.teacherRepository.findByLogin(teachersLogin).get();
        StudentEntity student = this.studentRepository.findByLogin(studentsLogin).get();

        return teacher.getStudents().contains(student);
    }
}
