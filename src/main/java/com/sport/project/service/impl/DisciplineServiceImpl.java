package com.sport.project.service.impl;

import com.sport.project.dao.entity.DisciplineEntity;
import com.sport.project.dao.repository.DisciplineRepository;
import com.sport.project.dao.repository.LessonsRepository;
import com.sport.project.dto.DisciplineDTO;
import com.sport.project.dto.LessonDTO;
import com.sport.project.exception.EntityNotFoundException;
import com.sport.project.mapper.Mapper;
import com.sport.project.service.DisciplineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DisciplineServiceImpl implements DisciplineService {

    private final DisciplineRepository disciplineRepository;
    private final LessonsRepository lessonsRepository;

    @Override
    public DisciplineDTO findById(Integer id) throws EntityNotFoundException {
        DisciplineEntity entity = this.disciplineRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Discipline with id %s not found", id)));
        return Mapper.map(entity);
    }

    @Override
    public DisciplineDTO findByName(String name) throws EntityNotFoundException {
        DisciplineEntity entity = this.disciplineRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Discipline with name %s not found", name)));
        return Mapper.map(entity);
    }

    @Override
    public List<DisciplineDTO> findAll() {
        return this.disciplineRepository
                .findAll()
                .stream()
                .map(Mapper::map)
                .toList();
    }

    @Override
    public List<LessonDTO> getLessons(Integer disciplineId) throws EntityNotFoundException {
        if (!disciplineRepository.existsById(disciplineId)) {
            throw new EntityNotFoundException("Дисциплина с ID: " + disciplineId + " не найдена");
        }

        return this.lessonsRepository.findByDisciplineId(disciplineId)
                .stream()
                .map(Mapper::map)
                .toList();
    }

    @Override
    public List<LessonDTO> getLessons(String disciplineName) throws EntityNotFoundException {
        if (!disciplineRepository.existsByName(disciplineName)) {
            throw new EntityNotFoundException("Дисциплина с названием: " + disciplineName + " не найдена");
        }

        return this.lessonsRepository.findByDiscipline_Name(disciplineName)
                .stream()
                .map(Mapper::map)
                .toList();
    }

    @Override
    public boolean existsByName(String name) {
        return this.disciplineRepository
                .existsByName(name);
    }
}
