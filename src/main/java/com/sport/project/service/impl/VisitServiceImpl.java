package com.sport.project.service.impl;

import com.sport.project.dao.entity.LessonsEntity;
import com.sport.project.dao.entity.StudentEntity;
import com.sport.project.dao.entity.VisitsEntity;
import com.sport.project.dao.repository.LessonsRepository;
import com.sport.project.dao.repository.StudentRepository;
import com.sport.project.dao.repository.VisitsRepository;
import com.sport.project.dto.LessonDTO;
import com.sport.project.dto.StudentDTO;
import com.sport.project.dto.VisitCreationDTO;
import com.sport.project.dto.VisitDTO;
import com.sport.project.exception.EntityAlreadyExistsException;
import com.sport.project.exception.EntityNotFoundException;
import com.sport.project.mapper.Mapper;
import com.sport.project.service.LessonService;
import com.sport.project.service.StudentService;
import com.sport.project.service.VisitService;
import com.sport.project.service.interfaces.visit.VisitCreationService;
import com.sport.project.service.interfaces.visit.VisitDeletingService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class VisitServiceImpl implements VisitService, VisitCreationService, VisitDeletingService {

    private final VisitsRepository visitsRepository;
    private final LessonsRepository lessonsRepository;
    private final StudentRepository studentRepository;

    @Override
    public VisitDTO findById(Integer id) throws EntityNotFoundException {
        VisitsEntity entity = this.visitsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Visit with id %s not found", id)));
        return Mapper.map(entity);
    }

    @Override
    public VisitDTO findByStudentAndLesson(String studentLogin, Integer lessonId) throws EntityNotFoundException {
        VisitsEntity entity = this.visitsRepository.findByStudentIdAndLessonId(studentLogin, lessonId)
                .orElseThrow(() -> new EntityNotFoundException("Not found"));
        return Mapper.map(entity);
    }

    @Override
    public List<VisitDTO> findAll() {
        return this.visitsRepository.findAll()
                .stream()
                .map(Mapper::map)
                .toList();
    }

    @Override
    public List<VisitDTO> findByStudent(String studentLogin) {
        return this.visitsRepository.findByStudentLogin(studentLogin)
                .stream()
                .map(Mapper::map)
                .toList();
    }

    @Override
    public List<VisitDTO> findByLesson(Integer lessonId) {
        return this.visitsRepository.findByLessonId(lessonId)
                .stream()
                .map(Mapper::map)
                .toList();
    }

    @Override
    public List<VisitDTO> findByDateRange(LocalDate from, LocalDate to) {
        return this.visitsRepository.findByDateRange(from, to)
                .stream()
                .map(Mapper::map)
                .toList();
    }

    @Override
    public boolean existsById(Integer id) {
        return this.visitsRepository.findById(id).isPresent();
    }


    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,
            rollbackFor = {
                    jakarta.persistence.EntityNotFoundException.class,
                    EntityAlreadyExistsException.class,
                    EntityNotFoundException.class
            })
    public VisitDTO create(VisitCreationDTO dto) throws EntityAlreadyExistsException {
        final String studentLogin = dto.getStudentLogin();
        final Integer lessonId = dto.getLessonId();
        final boolean exists = dto.isExists();

        log.info("Visit creation [1] started: {}, {}, {}", studentLogin, lessonId, exists);

        StudentEntity student = this.studentRepository.findByLogin(studentLogin)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Student with login %s not found", studentLogin)));

        // Использую этот метод, потому что нужно получить чисто id для установки связи, не нужно получать всю сущность
        LessonsEntity lesson = this.lessonsRepository.getReferenceById(lessonId);

        VisitsEntity visit = VisitsEntity.builder()
                .student(student)
                .lesson(lesson)
                .exists(exists)
                .build();

        this.visitsRepository.save(visit);

        log.info("Visit saved [1] {}", visit.getId());
        return Mapper.map(visit);
    }


    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,
            rollbackFor = {
            jakarta.persistence.EntityNotFoundException.class,
                    EntityAlreadyExistsException.class,
                    EntityNotFoundException.class
    })
    public VisitDTO create(String studentLogin, Integer lessonId, boolean isExists) throws EntityAlreadyExistsException {

        log.info("Visit creation [2] started: {}, {}, {}", studentLogin, lessonId, isExists);

        StudentEntity student = this.studentRepository.findByLogin(studentLogin)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Student with login %s not found", studentLogin)));

        LessonsEntity lesson = this.lessonsRepository.getReferenceById(lessonId);

        VisitsEntity visit = VisitsEntity.builder()
                .student(student)
                .lesson(lesson)
                .exists(isExists)
                .build();

        this.visitsRepository.save(visit);

        log.info("Visit saved [2] {}", visit.getId());

        return Mapper.map(visit);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {EntityNotFoundException.class, IllegalArgumentException.class, jakarta.persistence.EntityNotFoundException.class})
    public void deleteById(@NonNull Integer id) throws EntityNotFoundException {
        if (id <= 0) throw new IllegalArgumentException("ID cannot be less or equal zero");
        this.visitsRepository.deleteById(id);
        log.info("Deleting visit by id {} completed", id);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {EntityNotFoundException.class, IllegalArgumentException.class, jakarta.persistence.EntityNotFoundException.class})
    public void deleteByStudentLogin(@NonNull String studentLogin) throws EntityNotFoundException {
        if (studentLogin.isBlank()) throw new IllegalArgumentException("Login cannot be blank");
        this.visitsRepository.deleteByStudent_Login(studentLogin);
        log.info("Deleting visit by student login {} completed", studentLogin);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {EntityNotFoundException.class, IllegalArgumentException.class, jakarta.persistence.EntityNotFoundException.class})
    public void deleteByLesson(@NonNull Integer lessonId) throws EntityNotFoundException {
        if (lessonId <= 0) throw new IllegalArgumentException("");
        this.visitsRepository.deleteByLessonId(lessonId);
        log.info("Deleting visit by lesson id {} completed", lessonId);
    }
}
