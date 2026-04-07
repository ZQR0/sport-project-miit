package com.sport.project.service.impl;

import com.sport.project.dao.entity.SectionEntity;
import com.sport.project.dao.repository.SectionRepository;
import com.sport.project.dao.repository.StudentRepository;
import com.sport.project.dto.SectionDTO;
import com.sport.project.dto.StudentDTO;
import com.sport.project.exception.EntityNotFoundException;
import com.sport.project.mapper.Mapper;
import com.sport.project.service.SectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SectionServiceImpl implements SectionService {

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
}
