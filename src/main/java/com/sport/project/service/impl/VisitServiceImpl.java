package com.sport.project.service.impl;

import com.sport.project.dao.entity.LessonsEntity;
import com.sport.project.dao.entity.StudentEntity;
import com.sport.project.dao.entity.VisitsEntity;
import com.sport.project.dao.repository.LessonsRepository;
import com.sport.project.dao.repository.StudentRepository;
import com.sport.project.dao.repository.VisitsRepository;
import com.sport.project.dao.repository.projection.AttendanceProjection;
import com.sport.project.dto.*;
import com.sport.project.exception.EntityAlreadyExistsException;
import com.sport.project.exception.EntityNotFoundException;
import com.sport.project.mapper.Mapper;
import com.sport.project.service.VisitService;
import com.sport.project.service.interfaces.visit.VisitBusinessService;
import com.sport.project.service.interfaces.visit.VisitCreationService;
import com.sport.project.service.interfaces.visit.VisitDeletingService;
import com.sport.project.service.interfaces.visit.VisitUpdatingService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class VisitServiceImpl implements VisitService,
        VisitCreationService,
        VisitDeletingService,
        VisitUpdatingService,
        VisitBusinessService
{

    private final VisitsRepository visitsRepository;
    private final LessonsRepository lessonsRepository;
    private final StudentRepository studentRepository;

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public VisitDTO findById(Integer id) throws EntityNotFoundException {
        VisitsEntity entity = this.visitsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Visit with id %s not found", id)));
        return Mapper.map(entity);
    }

    @Override
    public VisitDTO findByStudentAndLesson(String studentLogin, Integer lessonId) throws EntityNotFoundException {
        VisitsEntity entity = this.visitsRepository.findByStudentLoginAndLessonId(studentLogin, lessonId)
                .orElseThrow(() -> new EntityNotFoundException("Not found"));
        return Mapper.map(entity);
    }

    @Override
    public List<VisitDTO> findAll() {
        return this.visitsRepository.findAllOptimized()
                .stream()
                .map(Mapper::map)
                .toList();
    }

    @Override
    public List<VisitDTO> findByStudent(String studentLogin) throws EntityNotFoundException {

        if (!this.studentRepository.existsByLogin(studentLogin)) {
            log.info("Student with login {} not found [findByStudent Method]", studentLogin);
            throw new EntityNotFoundException(String.format("Student with login %s not found", studentLogin));
        }

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

        if (from == null || to == null) {
            throw new IllegalArgumentException("Params of date cannot be null");
        }

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


    //TODO: Оттестировать после пд
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {EntityNotFoundException.class, IllegalArgumentException.class, jakarta.persistence.EntityNotFoundException.class})
    public void deleteByStudentLogin(@NonNull String studentLogin) throws EntityNotFoundException {
        if (studentLogin.isBlank()) throw new IllegalArgumentException("Login cannot be blank");

        if (!this.studentRepository.existsByLogin(studentLogin)) {
            log.info("Student with login {} not found [deleteByStudentLogin]", studentLogin);
            throw new EntityNotFoundException(String.format("Student with login %s not found", studentLogin));
        }

        this.visitsRepository.deleteByStudent_Login(studentLogin);
        log.info("Deleting visit by student login {} completed", studentLogin);
    }

    //TODO: оттестировать после пд
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {EntityNotFoundException.class, IllegalArgumentException.class, jakarta.persistence.EntityNotFoundException.class})
    public void deleteByLesson(@NonNull Integer lessonId) throws EntityNotFoundException {
        if (lessonId <= 0) throw new IllegalArgumentException("Lesson id cannot be negative");

        if (this.lessonsRepository.existsById(lessonId)) {
            log.info("Lesson with id {} not found", lessonId);
            throw new EntityNotFoundException(String.format("Lesson with id %s not found", lessonId));
        }

        this.visitsRepository.deleteByLessonId(lessonId);
        log.info("Deleting visit by lesson id {} completed", lessonId);
    }


    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {EntityNotFoundException.class, IllegalArgumentException.class, jakarta.persistence.EntityNotFoundException.class})
    public void updateStatus(Integer visitId, boolean isExists) throws EntityNotFoundException {
        log.info("Update status started");
        VisitsEntity visit = this.visitsRepository.findById(visitId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Visit with this id %s not found", visitId)));

        visit.setExists(isExists);
//        this.visitsRepository.save(visit);
        // save не нужен, т.к. merge hibernate сам вызывает
        log.info("Visit {} updated", visitId);
    }

    @Override
    @Transactional(
            readOnly = true,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = {
            jakarta.persistence.EntityNotFoundException.class,
            EntityAlreadyExistsException.class,
            EntityNotFoundException.class}
    )
    public Map<LocalDate, List<AttendanceInfo>> getStudentAttendanceMap(String studentLogin) throws EntityNotFoundException {

        if (!this.studentRepository.existsByLogin(studentLogin)) {
            throw new EntityNotFoundException(String.format("Student with login %s not found", studentLogin));
        }

        List<AttendanceProjection> projections = this.visitsRepository.findAttendanceByStudentLogin(studentLogin);

        return projections.stream()
                .collect(Collectors.groupingBy(
                        AttendanceProjection::getLessonDate,
                        Collectors.mapping(
                                proj -> new AttendanceInfo(proj.getIsExists(), proj.getStartAt(), proj.getEndAt()),
                                Collectors.toList()
                        )
                ));
    }

    @Override
    public int getTotalVisits(String studentLogin) throws EntityNotFoundException {
        StudentEntity student = this.studentRepository.findByLogin(studentLogin)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Student with login %s not found", studentLogin)));
        return student.getVisits().size();
    }

    @Override
    public int getTotalAbsences(String studentLogin) throws EntityNotFoundException {
        StudentEntity student = this.studentRepository.findByLogin(studentLogin)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Student with login %s not found", studentLogin)));

        return Math.toIntExact(student.getVisits()
                .stream()
                .filter(visit -> !visit.isExists())
                .count());
    }

    @Override
    public double getAttendancePercentage(String studentLogin) throws EntityNotFoundException {
        if (!this.studentRepository.existsByLogin(studentLogin)) {
            log.info("Student with login {} not found [getAttendancePercentage Method]", studentLogin);
            throw new EntityNotFoundException(String.format("Student with login %s not found", studentLogin));
        }

        List<VisitDTO> allVisits = this.findByStudent(studentLogin);
        int total = allVisits.size();

        // Посещенные занятия (isExists = true)
        int visitedCount = Math.toIntExact(allVisits.stream()
                .filter(VisitDTO::isExists)
                .count());

        return (double) visitedCount / total;
    }

    @Override
    public List<StudentDTO> getAbsentStudentsForLesson(Integer lessonId) throws EntityNotFoundException {

        if (!this.lessonsRepository.existsById(lessonId)) {
            throw new EntityNotFoundException(String.format("Lesson with id %s not found", lessonId));
        }

        List<StudentEntity> absentStudents = this.studentRepository.findAbsentStudentsForLesson(lessonId);

        if (absentStudents.isEmpty()) {
            log.info("Absent student list is empty");
        }

        return absentStudents.stream()
                .map(Mapper::mapWithoutExists)
                .toList();
    }

    @Override
    public void bulkMarkAttendance(Integer lessonId, Map<String, Boolean> attendanceMap) throws EntityNotFoundException {
        log.info("Log-заглушка");
    }
}
