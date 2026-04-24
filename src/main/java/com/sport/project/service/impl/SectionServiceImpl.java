package com.sport.project.service.impl;

import com.sport.project.dao.entity.SectionEntity;
import com.sport.project.dao.entity.StudentEntity;
import com.sport.project.dao.repository.SectionRepository;
import com.sport.project.dao.repository.StudentRepository;
import com.sport.project.dto.SectionCreationDTO;
import com.sport.project.dto.SectionDTO;
import com.sport.project.dto.StudentDTO;
import com.sport.project.exception.EntityAlreadyExistsException;
import com.sport.project.exception.EntityNotFoundException;
import com.sport.project.mapper.Mapper;
import com.sport.project.service.SectionService;
import com.sport.project.service.interfaces.section.SectionCreationService;
import com.sport.project.service.interfaces.section.SectionDeletingService;
import com.sport.project.service.interfaces.section.SectionUpdatingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SectionServiceImpl implements SectionService,
        SectionCreationService,
        SectionDeletingService,
        SectionUpdatingService
{

    private final SectionRepository sectionRepository;
    private final StudentRepository studentRepository;

    @Override
    public SectionDTO findById(Integer id) throws EntityNotFoundException {
        SectionEntity entity = this.sectionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Section with id %s not found", id)));

        return Mapper.map(entity);
    }

    @Override
    public SectionDTO findByName(String name) throws EntityNotFoundException {
        SectionEntity entity = this.sectionRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Section with name %s not found", name)));

        return Mapper.map(entity);
    }

    @Override
    public List<SectionDTO> findAll() {
        return this.sectionRepository
                .findAll()
                .stream()
                .map(Mapper::map)
                .toList();
    }

    @Override
    public List<StudentDTO> getStudents(Integer sectionId) throws EntityNotFoundException {
        if (!sectionRepository.existsById(sectionId)) {
            throw new EntityNotFoundException("Секция с ID " + sectionId + " не найдена");
        }

        //Получаю студентов секции
        return this.studentRepository.findBySectionId(sectionId)
                .stream()
                .map(Mapper::map)
                .toList();
    }

    @Override
    public List<StudentDTO> getStudents(String sectionName) throws EntityNotFoundException {
        if (!sectionRepository.existsByName(sectionName)) {
            throw new EntityNotFoundException("Секция с названием " + sectionName + " не найдена");
        }

        //Получаю студентов секции
        return this.studentRepository.findBySectionName(sectionName)
                .stream()
                .map(Mapper::map)
                .toList();
    }

    @Override
    public boolean existsByName(String name) {
        return this.sectionRepository
                .existsByName(name);
    }

    @Override
    @Transactional
    public SectionDTO create(SectionCreationDTO dto) throws EntityAlreadyExistsException {

        final String sectionName = dto.getName();
        final String sectionDescription = dto.getDescription();
        final List<StudentEntity> students = new ArrayList<>();

        log.info("Section creation [1] started: {}, {}, {}", sectionName, sectionDescription, students);

        if (this.existsByName(sectionName)) {
            throw new EntityAlreadyExistsException(
                    String.format("Section with name '%s' already exists", sectionName)
            );
        }

        SectionEntity entity = SectionEntity.builder()
                .name(sectionName)
                .description(sectionDescription)
                .studentsOnSection(students)
                .build();

        SectionEntity saved = sectionRepository.save(entity);

        return Mapper.map(saved);
    }

    @Override
    public SectionDTO create(String name, String description) throws EntityAlreadyExistsException {

        log.info("Section creation [2] started: {}, {}", name, description);

        SectionEntity section = SectionEntity.builder()
                .name(name)
                .description(description)
                .build();

        this.sectionRepository.save(section);

        log.info("Section saved [2] {}", section.getId());

        return Mapper.map(section);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) throws EntityNotFoundException {
        if (id < 0) throw new IllegalArgumentException("Section ID cannot be less or equal to zero");
        this.sectionRepository.deleteById(id);
        log.info("Deleting section by id {} completed", id);
    }

    @Override
    @Transactional
    public void deleteByName(String name) throws EntityNotFoundException {
        if (name.isBlank()) throw new IllegalArgumentException();
        this.sectionRepository.deleteByName(name);
        log.info("Deleting section by name {} completed", name);
    }

    @Override
    @Transactional
    public SectionDTO updateName(Integer sectionId, String name) throws EntityNotFoundException {
        log.info("Update name section started");
        SectionEntity section = this.sectionRepository.findById(sectionId).
                orElseThrow(() -> new EntityNotFoundException(String.format("Section with this id %s not found", sectionId)));

        section.setName(name);

        log.info("Section {} name updated to '{}'", sectionId, name);
        return Mapper.map(section);
    }

    @Override
    @Transactional
    public SectionDTO updateDescription(Integer id, String description) throws EntityNotFoundException {
        log.info("Update description section started");
        SectionEntity section = this.sectionRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException(String.format("Section with this id %s not found", id)));

        section.setDescription(description);

        log.info("Section {} description updated to '{}'", id, description);
        return Mapper.map(section);
    }
}
