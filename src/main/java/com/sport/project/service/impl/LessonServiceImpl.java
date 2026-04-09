package com.sport.project.service.impl;

import com.sport.project.dao.entity.DisciplineEntity;
import com.sport.project.dao.entity.LessonsEntity;
import com.sport.project.dao.entity.TeacherEntity;
import com.sport.project.dao.repository.DisciplineRepository;
import com.sport.project.dao.repository.LessonsRepository;
import com.sport.project.dao.repository.TeacherRepository;
import com.sport.project.dto.LessonCreationDTO;
import com.sport.project.dto.LessonDTO;
import com.sport.project.exception.EntityAlreadyExistsException;
import com.sport.project.exception.EntityNotFoundException;
import com.sport.project.mapper.Mapper;
import com.sport.project.service.LessonService;
import com.sport.project.service.interfaces.lesson.LessonCreationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LessonServiceImpl implements LessonService, LessonCreationService {

    private final LessonsRepository lessonsRepository;
    private final TeacherRepository teacherRepository;
    private final DisciplineRepository disciplineRepository;

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

        log.info("Lesson creation [1] started: {}, {}, {}", dateOfLesson, teacherId, disciplineId);

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

        log.info("Lesson saved [1] {}", lesson.getId());
        return Mapper.map(lesson);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,
            rollbackFor = {
                    jakarta.persistence.EntityNotFoundException.class,
                    EntityAlreadyExistsException.class,
                    EntityNotFoundException.class
            })
    public LessonDTO create(LocalDate dateOfLesson, Integer teacherId, Integer disciplineId) throws EntityAlreadyExistsException {
        log.info("Lesson creation [2] started: {}, {}, {}", dateOfLesson, teacherId, disciplineId);

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

        log.info("Lesson saved [2] {}", lesson.getId());
        return Mapper.map(lesson);
    }
}
