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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SectionServiceImpl implements SectionService,
        SectionCreationService
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
}
