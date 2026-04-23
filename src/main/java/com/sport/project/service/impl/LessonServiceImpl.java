package com.sport.project.service.impl;

import com.sport.project.dao.entity.*;
import com.sport.project.dao.repository.*;
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
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class LessonServiceImpl implements LessonService, LessonCreationService, LessonDeletingService, LessonUpdatingService, LessonBusinessService {

    private final LessonsRepository lessonsRepository;
    private final TeacherRepository teacherRepository;
    private final DisciplineRepository disciplineRepository;
    private final VisitsRepository visitsRepository;
    private final StudentRepository studentRepository;

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
        log.info("Getting attendance for lesson ID {}", lessonId);
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
        log.info("Marking attendance for lesson ID: {}, student login: {}, present: {}", lessonId, studentLogin, present);

        if (lessonId <= 0) {
            throw new IllegalArgumentException("Lesson id cannot be less or equal zero");
        }

        if (studentLogin == null || studentLogin.isBlank()) {
            throw new IllegalArgumentException("Student login cannot be null or empty");
        }

        if (!this.lessonsRepository.existsById(lessonId)) {
            throw new EntityNotFoundException(String.format("Lesson with id %s not found", lessonId));
        }

        StudentEntity student = this.studentRepository.findByLogin(studentLogin)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Student with login %s not found", studentLogin)));

        Optional<VisitsEntity> existingVisit = this.visitsRepository.findByLessonAndStudentLogin(lessonId, studentLogin);

        if (!existingVisit.isPresent()) {
            log.info("No visits found for lesson {} and student login {}", lessonId, studentLogin);
            return;
        }

        VisitsEntity visit = existingVisit.get();
        boolean oldValue = visit.isExists();

        if (oldValue == present) {
            log.info("Attendance for student {} in lesson {} already marked as {}",
                    studentLogin, lessonId, present);
        }

        visit.setExists(present);
        this.visitsRepository.save(visit);

        log.info("Updated attendance for student {} in lesson {}: {}",
                studentLogin, lessonId, present);
    }

    @Override
    public List<StudentDTO> getExpectedStudents(Integer lessonId) throws EntityNotFoundException {
        log.info("Getting expected students for lesson {}", lessonId);

        if (lessonId <= 0) {
            throw new IllegalArgumentException("Lesson id cannot be less or equal zero");
        }

        LessonsEntity lesson = this.lessonsRepository.findById(lessonId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Lesson with id %s not found", lessonId)));

        LocalDate lessonDate = lesson.getDateOfLesson();

        List<VisitsEntity> visits = this.visitsRepository.findByLessonIdWithStudent(lessonId);

        if (visits.isEmpty()) {
            log.info("No expected students found for lesson {}", lessonId);
            return Collections.emptyList();
        }

        List<StudentDTO> students = visits.stream()
                .map(visit -> {
                    StudentEntity student = visit.getStudent();

                    Map<LocalDate, Boolean> exist = new HashMap<>();
                    exist.put(lessonDate, visit.isExists());

                    return StudentDTO.builder()
                            .id(student.getId())
                            .firstName(student.getFullName().getFirstName())
                            .lastName(student.getFullName().getLastName())
                            .patronymic(student.getFullName().getPatronymic())
                            .login(student.getLogin())
                            .healthGroup(student.getHealthGroup().getId())
                            .exist(exist)
                            .build();
                })
                .toList();

        log.info("Found {} expected students for lesson {}", students.size(), lessonId);
        return students;
    }

    @Override
    public int getAttendanceCount(Integer lessonId) throws EntityNotFoundException {
        log.info("Getting attendance count for lesson {}", lessonId);

        List<VisitDTO> attendance = getAttendance(lessonId);
        int count = attendance.size();

        log.info("Found {} attendance marks for lesson {}", count, lessonId);
        return count;
    }

    @Override
    public boolean canDelete(Integer lessonId) throws EntityNotFoundException {
        log.info("Checking if lesson {} can be deleted", lessonId);

        if (lessonId <= 0) {
            throw new IllegalArgumentException("Lesson id cannot be less or equal zero");
        }

        if (!this.lessonsRepository.existsById(lessonId)) {
            throw new EntityNotFoundException(String.format("Lesson with id %s not found", lessonId));
        }

        long visitCount = this.visitsRepository.countByLessons_Id(lessonId);

        boolean canDelete = visitCount == 0;

        if (canDelete) {
            log.info("Lesson {} can be deleted - no visits found", lessonId);
        } else {
            log.info("Lesson {} cannot be deleted - has {} visits", lessonId, visitCount);
        }

        return canDelete;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {EntityNotFoundException.class, IllegalArgumentException.class, jakarta.persistence.EntityNotFoundException.class})
    public LessonDTO getWithFullDetails(Integer lessonId) throws EntityNotFoundException {
        log.info("Getting full details for lesson {}", lessonId);

        if (lessonId <= 0) {
            throw new IllegalArgumentException("Lesson id cannot be less or equal zero");
        }

        LessonsEntity lesson = this.lessonsRepository.findById(lessonId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Lesson with id %s not found", lessonId)));

        DisciplineEntity discipline = lesson.getDiscipline();
        TeacherEntity teacher = lesson.getTeacher();

        LessonDTO lessonDTO = LessonDTO.builder()
                .id(lesson.getId())
                .dateOfLesson(lesson.getDateOfLesson())
                .startAt(lesson.getStartAt())
                .endAt(lesson.getEndAt())
                .disciplineId(discipline != null ? discipline.getId() : null)
                .disciplineName(discipline != null ? discipline.getName() : null)
                .teacherId(teacher != null ? teacher.getId() : null)
                .teacherFullName(teacher.getFullName().getFirstName() + " " +
                        teacher.getFullName().getLastName() + " " +
                        teacher.getFullName().getPatronymic())
                .build();

        log.info("Full details loaded for lesson {}", lessonId);
        return lessonDTO;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {EntityNotFoundException.class, IllegalArgumentException.class, jakarta.persistence.EntityNotFoundException.class})
    public void bulkMarkAttendance(Integer lessonId, Map<String, Boolean> attendanceMap) throws EntityNotFoundException {
        log.info("Bulk marking attendance for lesson {}, {} students", lessonId, attendanceMap.size());

        if (lessonId <= 0) {
            throw new IllegalArgumentException("Lesson id cannot be less or equal zero");
        }

        if (attendanceMap == null || attendanceMap.isEmpty()) {
            throw new IllegalArgumentException("Attendance map cannot be null or empty");
        }

        if (!this.lessonsRepository.existsById(lessonId)) {
            throw new EntityNotFoundException(String.format("Lesson with id %s not found", lessonId));
        }

        LessonsEntity lesson = this.lessonsRepository.getReferenceById(lessonId);

        List<String> logins = new ArrayList<>(attendanceMap.keySet());
        List<StudentEntity> students = this.studentRepository.findByLoginIn(logins);
        Map<String, StudentEntity> studentMap = new HashMap<>();
        for (StudentEntity student : students) {
            studentMap.put(student.getLogin(), student);
        }

        List<VisitsEntity> existingVisits = this.visitsRepository.findByLessonId(lessonId);
        Map<Integer, VisitsEntity> visitMap = new HashMap<>();
        for (VisitsEntity visit : existingVisits) {
            Integer studentId = visit.getStudent().getId();
            visitMap.put(studentId, visit);
        }

        List<VisitsEntity> markedVisited = new ArrayList<>();

        for (Map.Entry<String, Boolean> entry : attendanceMap.entrySet()) {
            String login = entry.getKey();
            Boolean present = entry.getValue();

            StudentEntity student = studentMap.get(login);
            if (student == null) {
                log.warn("Student with login {} not found, skipping", login);
                continue;
            }

            VisitsEntity visit = visitMap.get(student.getId());
            if (visit != null) {
                visit.setExists(present);
                markedVisited.add(visit);
            }
        }

        this.visitsRepository.saveAll(markedVisited);
        log.info("Bulk attendance completed for lesson {}", lessonId);
    }

    private static void validateTime(LocalTime startTime, LocalTime endTime) {
        //TODO: валидация на то, что конец пары не раньше начала. Ещё нужно добавить валидацию, что временные промежутки входят в предел пары (типо начало в 8:30 а не в 8:40 и т.п.)
        //TODO: также нужно добавить валидацию на то, что в это время у преподавателя уже нет пары в это время (мб придётся новый метод в репозиторий засунуть, но это определять будем позже уже)
    }
}
