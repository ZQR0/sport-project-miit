package com.sport.project.service.impl;

import com.sport.project.dao.entity.GroupEntity;
import com.sport.project.dao.entity.HealthGroupsEntity;
import com.sport.project.dao.entity.StudentEntity;
import com.sport.project.dao.repository.GroupRepository;
import com.sport.project.dao.repository.HealthGroupRepository;
import com.sport.project.dao.repository.StudentRepository;
import com.sport.project.dto.StudentCreationDTO;
import com.sport.project.dto.StudentDTO;
import com.sport.project.exception.EntityAlreadyExistsException;
import com.sport.project.exception.EntityNotFoundException;
import com.sport.project.mapper.Mapper;
import com.sport.project.service.HealthGroupService;
import com.sport.project.service.StudentService;
import com.sport.project.service.interfaces.student.StudentCreationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService, StudentCreationService {

    private final StudentRepository studentRepository;
    private final HealthGroupRepository healthGroupRepository;
    private final GroupRepository groupRepository;

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
    public List<StudentDTO> findByFullName(String firstName, String lastName, String patronymic) throws EntityNotFoundException {
        List<StudentEntity> entity = this.studentRepository.findByFullNameFirstNameAndFullNameLastNameAndFullNamePatronymic
                (firstName, lastName, patronymic);
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
        return this.studentRepository
                .findByHealthGroupId(healthGroupId)
                .stream()
                .map(Mapper::map)
                .toList();
    }

    @Override
    public boolean existsByLogin(String login) {
        return this.studentRepository
                .existsByLogin(login);
    }

    @Override
    public StudentDTO create(StudentCreationDTO dto) throws EntityAlreadyExistsException {

        log.info("=== DEBUG CREATE STUDENT ===");
        log.info("login: {}", dto.getLogin());
        log.info("healthGroupId: {}", dto.getHealthGroupId());
        log.info("groupId: {}", dto.getGroupId());
        log.info("birthday: {}", dto.getBirthday());
        log.info("============================");

        final String studentLogin = dto.getLogin();
        final String password = dto.getPassword();

        final String firstName = dto.getFirstName();
        final String lastName = dto.getLastName();
        final String patronymic = dto.getPatronymic();
        final LocalDate birthday = dto.getBirthday();

        HealthGroupsEntity healthGroup = this.healthGroupRepository.findById(dto.getHealthGroupId())
                .orElseThrow(() -> new EntityNotFoundException("Health group not found"));

        GroupEntity group = this.groupRepository.findById(dto.getGroupId())
                .orElseThrow(() -> new EntityNotFoundException("Group not found"));

        if (this.existsByLogin(studentLogin)) {
            throw new EntityAlreadyExistsException(
                    String.format("Student with login '%s' already exists", studentLogin)
            );
        }

        StudentEntity entity = StudentEntity.builder()
                .login(studentLogin)
                .passwordHash(password)
                .firstName(firstName)
                .lastName(lastName)
                .patronymic(patronymic)
                .birthday(birthday)
                .healthGroup(healthGroup)
                .group(group)
                .build();

        StudentEntity saved = studentRepository.save(entity);

        return Mapper.map(saved);
    }
}
