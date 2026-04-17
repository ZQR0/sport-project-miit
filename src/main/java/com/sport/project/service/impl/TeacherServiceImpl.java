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
import com.sport.project.service.interfaces.teacher.TeacherDeletingService;
import com.sport.project.service.interfaces.teacher.TeacherUpdatingService;
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
public class TeacherServiceImpl implements
        TeacherService, TeacherCreationService, TeacherDeletingService, TeacherUpdatingService {

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

    @Override
    @Transactional
    public void deleteByID(int id) throws EntityNotFoundException {
        TeacherEntity teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Teacher with ID " + id + " not found"));

        teacherRepository.delete(teacher);
    }

    @Override
    @Transactional
    public void deleteByLogin(String login) throws EntityNotFoundException {
        TeacherEntity teacher = teacherRepository.findByLogin(login)
                .orElseThrow(() -> new EntityNotFoundException("Teacher with login '" + login + "' not found"));

        teacherRepository.delete(teacher);
    }

    @Override
    @Transactional
    public void updateFullName(String firstName, String lastName, String patronymic, String login) throws EntityNotFoundException {
        TeacherEntity teacher = teacherRepository.findByLogin(login)
                .orElseThrow(() -> new EntityNotFoundException("Teacher with login '" + login + "' not found"));

        String oldFirstName = teacher.getFullName().getFirstName();
        String oldLastName = teacher.getFullName().getLastName();
        String oldPatronymic = teacher.getFullName().getPatronymic();

        log.info("Updating teacher full name: {} {} {} (login: {}) -> new: {} {} {}",
                oldLastName, oldFirstName, oldPatronymic != null ? oldPatronymic : "",
                login,
                lastName != null ? lastName : oldLastName,
                firstName != null ? firstName : oldFirstName,
                patronymic != null ? patronymic : (oldPatronymic != null ? oldPatronymic : ""));

        if (firstName != null && !firstName.isBlank()) {
            teacher.getFullName().setFirstName(firstName);
        }
        if (lastName != null && !lastName.isBlank()) {
            teacher.getFullName().setLastName(lastName);
        }
        if (patronymic != null && !patronymic.isBlank()) {
            teacher.getFullName().setPatronymic(patronymic);
        }

        log.info("Updated teacher full name: {} {} {} (login: {})",
                teacher.getFullName().getLastName(),
                teacher.getFullName().getFirstName(),
                teacher.getFullName().getPatronymic() != null ? teacher.getFullName().getPatronymic() : "",
                login);
    }

    @Override
    @Transactional
    public void updateLogin(String newLogin, String oldLogin) throws EntityNotFoundException, EntityAlreadyExistsException {
        TeacherEntity teacher = teacherRepository.findByLogin(oldLogin)
                .orElseThrow(() -> new EntityNotFoundException("Teacher with login '" + oldLogin + "' not found"));

        if (existsByLogin(newLogin)) {
            throw new EntityAlreadyExistsException("Login '" + newLogin + "' is already taken");
        }

        teacher.setLogin(newLogin);
        log.info("Updated login for teacher: {} -> {}", oldLogin, newLogin);
    }

    @Override
    @Transactional
    public void updateModerator(String login, boolean moderator) throws EntityNotFoundException {
        TeacherEntity teacher = teacherRepository.findByLogin(login)
                .orElseThrow(() -> new EntityNotFoundException("Teacher with login '" + login + "' not found"));

        teacher.setModerator(moderator);
        log.info("Updated moderator status for teacher {}: {}", login, moderator);
    }
}
