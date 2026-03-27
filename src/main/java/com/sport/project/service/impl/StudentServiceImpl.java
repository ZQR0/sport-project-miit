package com.sport.project.service.impl;

import com.sport.project.dao.entity.StudentEntity;
import com.sport.project.dao.repository.StudentRepository;
import com.sport.project.dto.StudentDTO;
import com.sport.project.exception.EntityNotFoundException;
import com.sport.project.mapper.Mapper;
import com.sport.project.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public StudentDTO findById(Integer id) throws EntityNotFoundException {
        StudentEntity entity = this.studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Student with id %s not found", id)));

        return Mapper.map(entity);
    }

    @Override
    public StudentDTO findByLogin(String login) throws EntityNotFoundException {
        StudentEntity entity = this.studentRepository.findByLogin(login)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Student with login %s not found", login)));

        return Mapper.map(entity);
    }

    @Override
    public List<StudentDTO> findByFullname(String firstName, String lastName, String patronymic) throws EntityNotFoundException {
        List<StudentEntity> entity = this.studentRepository.findByFullNameAndFullLastNameAndFullNamePatronymic(firstName, lastName, patronymic);
        return entity.stream()
                .map(Mapper::map)
                .toList();
    }

    @Override
    public List<StudentDTO> findAll() {
        return this.studentRepository
                .findAll()
                .stream()
                .map(Mapper::map)
                .toList();
    }

    @Override
    public List<StudentDTO> findByGroup(Integer groupId) {
        return this.studentRepository
                .findByGroupId(groupId)
                .stream()
                .map(Mapper::map)
                .toList();
    }

    @Override
    public List<StudentDTO> findBySection(Integer sectionId) {
        return this.studentRepository
                .findBySectionId(sectionId)
                .stream()
                .map(Mapper::map)
                .toList();
    }

    @Override
    public List<StudentDTO> findByHealthGroup(Integer healthGroupId) {
        return List.of();
    }

    @Override
    public boolean existsByLogin(String login) {
        return false;
    }
}
