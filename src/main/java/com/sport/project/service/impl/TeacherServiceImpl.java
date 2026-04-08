package com.sport.project.service.impl;

import com.sport.project.dao.entity.LessonsEntity;
import com.sport.project.dao.entity.TeacherEntity;
import com.sport.project.dao.repository.LessonsRepository;
import com.sport.project.dao.repository.TeacherRepository;
import com.sport.project.dto.TeacherCreationDTO;
import com.sport.project.dto.TeacherDTO;
import com.sport.project.exception.EntityAlreadyExistsException;
import com.sport.project.exception.EntityNotFoundException;
import com.sport.project.mapper.Mapper;
import com.sport.project.service.TeacherService;
import com.sport.project.service.interfaces.teacher.TeacherCreationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeacherServiceImpl implements TeacherService, TeacherCreationService {

    private final TeacherRepository teacherRepository;
    private final LessonsRepository lessonsRepository;

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
    public List<TeacherDTO> findByFullName(String firstName, String lastName, String patronymic) throws EntityNotFoundException {
        List<TeacherEntity> entities = this.teacherRepository.findByFullNameFirstNameAndFullNameLastNameAndFullNamePatronymic
                (firstName, lastName, patronymic);
        return entities.stream()
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
        List<TeacherEntity> entities = this.teacherRepository.findByIsModerator(true);

        return entities.stream()
                .map(Mapper::map)
                .toList();
    }

    @Override
    public List<TeacherDTO> findByLessonsDate(LocalDate date) {
        List<LessonsEntity> lessons = lessonsRepository.findByDateOfLesson(date);

        List<TeacherEntity> teachers = lessons.stream()
                .map(LessonsEntity::getTeacher)
                .filter(Objects::nonNull)
                .distinct()
                .toList();

        return teachers.stream()
                .map(Mapper::map)
                .toList();
    }

    @Override
    public boolean existsByLogin(String login) {
        Optional<TeacherEntity> entityOptional = this.teacherRepository.findByLogin(login);

        return entityOptional.isPresent();
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,
            rollbackFor = {
                    jakarta.persistence.EntityNotFoundException.class,
                    EntityAlreadyExistsException.class,
                    EntityNotFoundException.class
            })
    public TeacherDTO create(TeacherCreationDTO dto) throws EntityAlreadyExistsException {
        final String teacherLogin = dto.getLogin();

        final String firstName = dto.getFirstName();
        final String lastName = dto.getLastName();
        final String patronymic = dto.getPatronymic();
        final LocalDate birthday = dto.getBirthday();

        final String password = dto.getPassword();
        final boolean moderator = dto.getModerator();

        if (this.existsByLogin(teacherLogin)) {
            throw new EntityAlreadyExistsException(
                    String.format("Teacher with login '%s' already exists", dto.getLogin())
            );
        }

        TeacherEntity entity = TeacherEntity.builder()
                .login(teacherLogin)
                .firstName(firstName)
                .lastName(lastName)
                .patronymic(patronymic)
                .passwordHash(password)
                .moderator(moderator)
                .birthday(birthday)
                .build();

        TeacherEntity saved = teacherRepository.save(entity);

        return Mapper.map(saved);
    }
}
