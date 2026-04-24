package com.sport.project.service.impl;

import com.sport.project.dao.entity.DisciplineEntity;
import com.sport.project.dao.entity.LessonsEntity;
import com.sport.project.dao.entity.TeacherEntity;
import com.sport.project.dao.entity.VisitsEntity;
import com.sport.project.dao.repository.DisciplineRepository;
import com.sport.project.dao.repository.LessonsRepository;
import com.sport.project.dao.repository.TeacherRepository;
import com.sport.project.dao.repository.VisitsRepository;
import com.sport.project.dto.LessonCreationDTO;
import com.sport.project.dto.LessonDTO;
import com.sport.project.dto.StudentDTO;
import com.sport.project.dto.VisitDTO;
import com.sport.project.exception.EntityAlreadyExistsException;
import com.sport.project.exception.EntityNotFoundException;
import com.sport.project.mapper.Mapper;
import com.sport.project.service.LessonService;
import com.sport.project.service.interfaces.lesson.LessonBusinessService;
import com.sport.project.service.interfaces.lesson.LessonCreationService;
import com.sport.project.service.interfaces.lesson.LessonDeletingService;
import com.sport.project.service.interfaces.lesson.LessonUpdatingService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class LessonServiceImpl implements LessonService, LessonCreationService, LessonDeletingService, LessonUpdatingService, LessonBusinessService {

    private final LessonsRepository lessonsRepository;
    private final TeacherRepository teacherRepository;
    private final DisciplineRepository disciplineRepository;
    private final VisitsRepository visitsRepository;

    @Override
    public LessonDTO findById(Integer id) throws EntityNotFoundException {
        LessonsEntity entity = this.lessonsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Lesson with id %s not found", id)));

        return Mapper.map(entity);
    }

    @Override
    public LessonDTO findByDisciplineAndDate(Integer disciplineId, LocalDate date) throws EntityNotFoundException {
        LessonsEntity entity = this.lessonsRepository.findByDisciplineAndDate(disciplineId, date)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Lesson with discipline id "
                        + disciplineId + " or with date " + date + " not found")
                ));

        return Mapper.map(entity);
    }

    @Override
    public List<LessonDTO> findAll() {
        return this.lessonsRepository.findAll()
                .stream()
                .map(Mapper::map)
                .toList();
    }

    @Override
    public List<LessonDTO> findByDate(LocalDate date) {
        return this.lessonsRepository.findByDateOfLesson(date)
                .stream()
                .map(Mapper::map)
                .toList();
    }

    @Override
    public List<LessonDTO> findByTeacher(Integer teacherId) {
        return this.lessonsRepository.findByTeacherId(teacherId)
                .stream()
                .map(Mapper::map)
                .toList();
    }

    @Override
    public List<LessonDTO> findByDiscipline(Integer disciplineId) throws EntityNotFoundException {
        List<LessonsEntity> entity = this.lessonsRepository.findByDisciplineId(disciplineId);

        return entity.stream()
                .map(Mapper::map)
                .toList();
    }

    @Override
    public List<LessonDTO> findByDisciplineName(String disciplineName) throws EntityNotFoundException {
        List<LessonsEntity> entity = this.lessonsRepository.findByDiscipline_Name(disciplineName);

        return entity.stream()
                .map(Mapper::map)
                .toList();
    }

    @Override
    public List<LessonDTO> findByDateRange(LocalDate from, LocalDate to) {
        return this.lessonsRepository.findByDateRange(from, to)
                .stream()
                .map(Mapper::map)
                .toList();
    }

    @Override
    public boolean existsById(Integer id) {
        return this.lessonsRepository.findById(id).isPresent();
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,
            rollbackFor = {
                    jakarta.persistence.EntityNotFoundException.class,
                    EntityAlreadyExistsException.class,
                    EntityNotFoundException.class
            })
    public LessonDTO create(LessonCreationDTO dto) throws EntityAlreadyExistsException {
        final LocalDate dateOfLesson = dto.getDateOfLesson();
        final Integer teacherId = dto.getTeacherId();
        final Integer disciplineId = dto.getDisciplineId();
        final LocalTime startAt = dto.getStartAt();
        final LocalTime endAt = dto.getEndAt();

        validateTime(startAt, endAt);

        log.info("Lesson creation [DTO] started: {}, {}, {}", dateOfLesson, teacherId, disciplineId);

        TeacherEntity teacher = this.teacherRepository.findById(teacherId)
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException(String.format("Teacher with id %s not found", teacherId)));

        DisciplineEntity discipline = this.disciplineRepository.findById(disciplineId)
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException(String.format("Discipline with id %s not found", disciplineId)));

        LessonsEntity lesson = LessonsEntity.builder()
                .dateOfLesson(dateOfLesson)
                .teacher(teacher)
                .discipline(discipline)
                .startAt(startAt)
                .endAt(endAt)
                .build();

        this.lessonsRepository.save(lesson);

        log.info("Lesson saved [DTO] {}", lesson.getId());
        return Mapper.map(lesson);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,
            rollbackFor = {
                    jakarta.persistence.EntityNotFoundException.class,
                    EntityAlreadyExistsException.class,
                    EntityNotFoundException.class
            })
    //TODO: можно в целом этот метод не использовать и не обновлять, пока делаем всё через DTO
    public LessonDTO create(LocalDate dateOfLesson, Integer teacherId, Integer disciplineId) throws EntityAlreadyExistsException {
        log.info("Lesson creation [PARAMS] started: {}, {}, {}", dateOfLesson, teacherId, disciplineId);

        TeacherEntity teacher = this.teacherRepository.findById(teacherId)
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException(String.format("Teacher with id %s not found", teacherId)));

        DisciplineEntity discipline = this.disciplineRepository.findById(disciplineId)
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException(String.format("Discipline with id %s not found", disciplineId)));

        LessonsEntity lesson = LessonsEntity.builder()
                .dateOfLesson(dateOfLesson)
                .teacher(teacher)
                .discipline(discipline)
                .build();

        this.lessonsRepository.save(lesson);

        log.info("Lesson saved [PARAMS] {}", lesson.getId());
        return Mapper.map(lesson);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {EntityNotFoundException.class, IllegalArgumentException.class, jakarta.persistence.EntityNotFoundException.class})
    public void deleteById(@NonNull Integer id) throws EntityNotFoundException {
        if (id <= 0) throw new IllegalArgumentException("ID cannot be less or equal zero");
        this.lessonsRepository.deleteById(id);
        log.info("Deleting lesson by id {} completed", id);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {EntityNotFoundException.class, IllegalArgumentException.class, jakarta.persistence.EntityNotFoundException.class})
    public void deleteByDiscipline(@NonNull Integer disciplineId) throws EntityNotFoundException {
        if (disciplineId <= 0) throw new IllegalArgumentException("Discipline id cannot be less or equal zero");

        if (!this.disciplineRepository.existsById(disciplineId)) {
            log.info("Discipline with id {} not found", disciplineId);
            throw new EntityNotFoundException(String.format("Discipline with id %s not found", disciplineId));
        }

        this.lessonsRepository.deleteByDisciplineId(disciplineId);
        log.info("Deleting lesson by discipline id {} completed", disciplineId);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {EntityNotFoundException.class, IllegalArgumentException.class, jakarta.persistence.EntityNotFoundException.class})
    public void deleteByTeacher(@NonNull Integer teacherId) throws EntityNotFoundException {
        if (teacherId <= 0) throw new IllegalArgumentException("Teacher id cannot be less or equal zero");

        if (!this.teacherRepository.existsById(teacherId)) {
            log.info("Teacher with id {} not found", teacherId);
            throw new EntityNotFoundException(String.format("Teacher with id %s not found", teacherId));
        }

        this.lessonsRepository.deleteByTeacher(teacherId);
        log.info("Deleting lesson by teacher id {} completed", teacherId);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {EntityNotFoundException.class, IllegalArgumentException.class, jakarta.persistence.EntityNotFoundException.class})
    public void deleteByDate(@NonNull LocalDate date) throws EntityNotFoundException {
        List<LessonsEntity> lessons = this.lessonsRepository.findByDateOfLesson(date);
        if (lessons.isEmpty()) {
            log.info("No lessons found for date {}", date);
            throw new EntityNotFoundException(String.format("No lessons found for date %s", date));
        }

        this.lessonsRepository.deleteByDate(date);
        log.info("Deleting lessons by date {} completed. Deleted {} lessons", date, lessons.size());
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {EntityNotFoundException.class, IllegalArgumentException.class, jakarta.persistence.EntityNotFoundException.class})
    public LessonDTO updateDate(Integer lessonId, LocalDate newDate) throws EntityNotFoundException {
        log.info("Update date started");

        if (lessonId <= 0) {
            throw new IllegalArgumentException("Lesson id cannot be less or equal zero");
        }

        if (newDate == null) {
            throw new IllegalArgumentException("New date cannot be null");
        }

        LessonsEntity lesson = this.lessonsRepository.findById(lessonId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Lesson with id %s not found", lessonId)));

        LocalDate oldDate = lesson.getDateOfLesson();
        lesson.setDateOfLesson(newDate);

        log.info("Lesson {} date updated from {} to {}", lessonId, oldDate, newDate);
        return Mapper.map(lesson);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {EntityNotFoundException.class, IllegalArgumentException.class, jakarta.persistence.EntityNotFoundException.class})
    public LessonDTO updateTeacher(Integer lessonId, Integer teacherId) throws EntityNotFoundException {
        log.info("Update teacher id started");

        if (lessonId <= 0) {
            throw new IllegalArgumentException("Lesson id cannot be less or equal zero");
        }

        if (teacherId <= 0) {
            throw new IllegalArgumentException("Teacher id cannot be less or equal zero");
        }

        LessonsEntity lesson = this.lessonsRepository.findById(lessonId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Lesson with id %s not found", lessonId)));

        TeacherEntity newTeacher = this.teacherRepository.findById(teacherId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Teacher with id %s not found", teacherId)));

        TeacherEntity oldTeacher = lesson.getTeacher();
        lesson.setTeacher(newTeacher);

        log.info("Lesson {} teacher updated from '{}' to '{}'", lessonId, oldTeacher.getFullName(), newTeacher.getFullName());
        return Mapper.map(lesson);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {EntityNotFoundException.class, IllegalArgumentException.class, jakarta.persistence.EntityNotFoundException.class})
    public LessonDTO updateDiscipline(Integer lessonId, Integer disciplineId) throws EntityNotFoundException {
        log.info("Update discipline id started");

        if (lessonId <= 0) {
            throw new IllegalArgumentException("Lesson id cannot be less or equal zero");
        }

        if (disciplineId <= 0) {
            throw new IllegalArgumentException("Discipline id cannot be less or equal zero");
        }

        LessonsEntity lesson = this.lessonsRepository.findById(lessonId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Lesson with id %s not found", lessonId)));

        DisciplineEntity newDiscipline = this.disciplineRepository.findById(disciplineId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Discipline with id %s not found", disciplineId)));

        DisciplineEntity oldDiscipline = lesson.getDiscipline();
        lesson.setDiscipline(newDiscipline);

        log.info("Lesson {} discipline updated from '{}' to '{}'", lessonId, oldDiscipline.getName(), newDiscipline.getName());
        return Mapper.map(lesson);
    }

    @Override
    public List<VisitDTO> getAttendance(Integer lessonId) throws EntityNotFoundException {
        if (lessonId <= 0) {
            throw new IllegalArgumentException("Lesson id cannot be less or equal zero");
        }

        if (!this.lessonsRepository.existsById(lessonId)) {
            throw new EntityNotFoundException(String.format("Lesson with id %s not found", lessonId));
        }

        List<VisitsEntity> visits = this.visitsRepository.findByLessonIdWithStudent(lessonId);

        if (visits.isEmpty()) {
            log.info("No visits found for lesson {}", lessonId);
            return Collections.emptyList();
        }

        List<VisitDTO> attendance = visits.stream()
                .map(visit -> VisitDTO.builder()
                        .id(visit.getId())
                        .studentId(visit.getStudent().getId())
                        .studentLogin(visit.getStudent().getLogin())
                        .lessonId(lessonId)
                        .isExists(visit.isExists())
                        .build())
                .toList();

        log.info("Found {} visits for lesson {}", attendance.size(), lessonId);
        return attendance;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {EntityNotFoundException.class, IllegalArgumentException.class, jakarta.persistence.EntityNotFoundException.class})
    public void markAttendance(Integer lessonId, String studentLogin, boolean present) throws EntityNotFoundException {
        if (lessonId <= 0) {
            throw new IllegalArgumentException("Lesson id cannot be less or equal zero");
        }

        if (studentLogin == null || studentLogin.isBlank()) {
            throw new IllegalArgumentException("Student login cannot be null or empty");
        }
    }

    @Override
    public List<StudentDTO> getExpectedStudents(Integer lessonId) throws EntityNotFoundException {
        return List.of();
    }

    @Override
    public int getAttendanceCount(Integer lessonId) throws EntityNotFoundException {
        return 0;
    }

    @Override
    public boolean canDelete(Integer lessonId) throws EntityNotFoundException {
        return true;
    }

    @Override
    public LessonDTO getWithFullDetails(Integer lessonId) throws EntityNotFoundException {
        return null;
    }

    @Override
    public void bulkMarkAttendance(Integer lessonId, Map<String, Boolean> attendanceMap) throws EntityNotFoundException {
        log.info("Log-заглушка");
    }

    private static void validateTime(LocalTime startTime, LocalTime endTime) {
        //TODO: валидация на то, что конец пары не раньше начала. Ещё нужно добавить валидацию, что временные промежутки входят в предел пары (типо начало в 8:30 а не в 8:40 и т.п.)
        //TODO: также нужно добавить валидацию на то, что в это время у преподавателя уже нет пары в это время (мб придётся новый метод в репозиторий засунуть, но это определять будем позже уже)
    }
}
