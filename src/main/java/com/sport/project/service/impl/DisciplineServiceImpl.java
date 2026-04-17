package com.sport.project.service.impl;

import com.sport.project.dao.entity.DisciplineEntity;
import com.sport.project.dao.entity.LessonsEntity;
import com.sport.project.dao.entity.TeacherEntity;
import com.sport.project.dao.repository.DisciplineRepository;
import com.sport.project.dao.repository.LessonsRepository;
import com.sport.project.dto.*;
import com.sport.project.exception.EntityAlreadyExistsException;
import com.sport.project.exception.EntityNotFoundException;
import com.sport.project.mapper.Mapper;
import com.sport.project.service.DisciplineService;
import com.sport.project.service.interfaces.discipline.DisciplineBusinessService;
import com.sport.project.service.interfaces.discipline.DisciplineCreationService;
import com.sport.project.service.interfaces.discipline.DisciplineDeletingService;
import com.sport.project.service.interfaces.discipline.DisciplineUpdatingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DisciplineServiceImpl implements DisciplineService, DisciplineCreationService, DisciplineBusinessService, DisciplineDeletingService, DisciplineUpdatingService {

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
            throw new EntityNotFoundException(String.format("Discipline with id %s not found", disciplineId));
        }

        return this.lessonsRepository.findByDisciplineId(disciplineId)
                .stream()
                .map(Mapper::map)
                .toList();
    }

    @Override
    public List<LessonDTO> getLessons(String disciplineName) throws EntityNotFoundException {
        if (!disciplineRepository.existsByName(disciplineName)) {
            throw new EntityNotFoundException(String.format("Discipline with name %s not found", disciplineName));
        }

        return this.lessonsRepository.findByDiscipline_Name(disciplineName)
                .stream()
                .map(Mapper::map)
                .toList();
    }

    @Override
    public DisciplineDTO create(DisciplineCreationDTO dto) throws EntityAlreadyExistsException {

        final String disciplineName = dto.getName();

        if (this.existsByName(disciplineName)) {
            throw new EntityAlreadyExistsException(
                    String.format("Discipline with name '%s' already exists", dto.getName())
            );
        }

        DisciplineEntity entity = DisciplineEntity.builder()
                .name(disciplineName)
                .build();

        DisciplineEntity saved = disciplineRepository.save(entity);

        return Mapper.map(saved);
    }

    @Override
    public DisciplineDTO create(String name) throws EntityAlreadyExistsException {

        if (this.existsByName(name)) {
            throw new EntityAlreadyExistsException(
                    String.format("Discipline with name '%s' already exists", name)
            );
        }

        DisciplineEntity entity = DisciplineEntity.builder()
                .name(name)
                .build();

        this.disciplineRepository.save(entity);

        return Mapper.map(entity);
    }

    @Override
    public boolean existsByName(String name) {
        return this.disciplineRepository
                .existsByName(name);
    }

    @Override
    public int getLessonCount(Integer disciplineId) throws EntityNotFoundException {
        DisciplineEntity discipline = this.disciplineRepository.findById(disciplineId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Discipline with id %s not found", disciplineId)));
        return discipline.getLessonsEntities().size();
    }

    @Override
    public List<LessonDTO> getLessonsByDateRange(Integer disciplineId, LocalDate from, LocalDate to) throws EntityNotFoundException {

        if (!disciplineRepository.existsById(disciplineId)) {
            throw new EntityNotFoundException(String.format("Discipline with id %s not found", disciplineId));
        }

        if (from == null || to == null) {
            throw new IllegalArgumentException("Params of date cannot be null");
        }

        return this.disciplineRepository.getLessonsByDateRange(disciplineId, from, to)
                .stream()
                .map(Mapper::map)
                .toList();
    }

    // FIXME будто метод дубликат getLessons (ну или поиска занятий по айди)
    @Override
    public List<LessonDTO> getLessonsWithTeacher(Integer disciplineId) throws EntityNotFoundException {

        if (!disciplineRepository.existsById(disciplineId)) {
            throw new EntityNotFoundException(String.format("Discipline with id %s not found", disciplineId));
        }

        return this.lessonsRepository.findByDisciplineId(disciplineId)
                .stream()
                .map(Mapper::map)
                .toList();
    }

    @Override
    public boolean canDelete(Integer disciplineId) throws EntityNotFoundException {
        if (!disciplineRepository.existsById(disciplineId)) {
            throw new EntityNotFoundException(String.format("Discipline with id %s not found", disciplineId));
        }
        return this.disciplineRepository.canDelete(disciplineId);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {EntityNotFoundException.class, IllegalArgumentException.class, jakarta.persistence.EntityNotFoundException.class})
    public void deleteById(Integer id) throws EntityNotFoundException {
        if (id <= 0) throw new IllegalArgumentException("ID cannot be less or equal zero");
        this.disciplineRepository.deleteById(id);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {EntityNotFoundException.class, IllegalArgumentException.class, jakarta.persistence.EntityNotFoundException.class})
    public void deleteByName(String disciplineName) throws EntityNotFoundException {
        if (!disciplineRepository.existsByName(disciplineName)) {
            throw new EntityNotFoundException(String.format("Discipline with name %s not found", disciplineName));
        }
        this.disciplineRepository.deleteByName(disciplineName);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {EntityNotFoundException.class, IllegalArgumentException.class, jakarta.persistence.EntityNotFoundException.class})
    public DisciplineDTO updateName(Integer disciplineId, String disciplineName) throws EntityNotFoundException {
        DisciplineEntity discipline = this.disciplineRepository.findById(disciplineId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Discipline with id %s not found", disciplineId)));

        discipline.setName(disciplineName);
        this.disciplineRepository.save(discipline);

        return Mapper.map(discipline);
    }
}
