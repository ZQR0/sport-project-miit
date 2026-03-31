package com.sport.project.service.impl;

import com.sport.project.dao.entity.DisciplineEntity;
import com.sport.project.dao.repository.DisciplineRepository;
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

    //TODO: вотэту какашку доделать
    @Override
    public List<LessonDTO> getLessons(Integer disciplineId) throws EntityNotFoundException {
        return List.of();
    }

    @Override
    public List<LessonDTO> getLessons(String disciplineName) throws EntityNotFoundException {
        return List.of();
    }

    @Override
    public boolean existsByName(String name) {
        return this.disciplineRepository
                .existsByName(name);
    }
}
