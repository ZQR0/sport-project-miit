package com.sport.project.service.impl;

import com.sport.project.dao.entity.LessonsEntity;
import com.sport.project.dao.repository.LessonsRepository;
import com.sport.project.dto.LessonDTO;
import com.sport.project.exception.EntityNotFoundException;
import com.sport.project.mapper.Mapper;
import com.sport.project.service.LessonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LessonServiceImpl implements LessonService {

    private final LessonsRepository lessonsRepository;

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
}
