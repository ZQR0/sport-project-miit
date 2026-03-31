package com.sport.project.service.impl;

import com.sport.project.dao.entity.TeacherEntity;
import com.sport.project.dao.repository.TeacherRepository;
import com.sport.project.dto.TeacherDTO;
import com.sport.project.exception.EntityNotFoundException;
import com.sport.project.mapper.Mapper;
import com.sport.project.service.TeacherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    @Override
    public TeacherDTO findById(Integer id) throws EntityNotFoundException {
        TeacherEntity entity = this.teacherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Teacher with id %s not found", id)));

        return Mapper.map(entity);
    }

    @Override
    public TeacherDTO findByLogin(String login) throws EntityNotFoundException {
        TeacherEntity entity = this.teacherRepository.findByLogin(login)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Teacher with login %s not found", login)));

        return Mapper.map(entity);
    }

    @Override
    public List<TeacherDTO> findByFullname(String firstName, String lastName, String patronymic) throws EntityNotFoundException {
        List<TeacherEntity> entityList = this.teacherRepository.findByFullNameFirstNameAndFullNameLastNameAndFullNamePatronymic(firstName, lastName, patronymic);
        return entityList.stream()
                .map(Mapper::map)
                .toList();
    }

    @Override
    public List<TeacherDTO> findAll() {
        return this.teacherRepository
                .findAll()
                .stream()
                .map(Mapper::map)
                .toList();
    }

    @Override
    public List<TeacherDTO> findAllModerators() {
        return this.teacherRepository
                .findByIsModeratorTrue()
                .stream()
                .map(Mapper::map)
                .toList();
    }

    @Override
    public List<TeacherDTO> findByLessonsDate(LocalDate date) {
        return List.of();
    }

    //проверка существования учителя по логину
    @Override
    public boolean existsByLogin(String login) {
        return this.teacherRepository.existsByLogin(login);
    }
}
