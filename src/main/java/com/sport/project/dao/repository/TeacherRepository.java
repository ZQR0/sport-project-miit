package com.sport.project.dao.repository;

import com.sport.project.dao.entity.TeacherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeacherRepository extends JpaRepository<TeacherEntity, Integer> {

    Optional<TeacherEntity> findByLogin(String login);

    List<TeacherEntity> findByIsModerator(boolean isModerator);

    List<TeacherEntity> findByFullNameFirstName(String firstName);

    List<TeacherEntity> findByFullNameLastName(String lastName);

    List<TeacherEntity> findByFullNamePatronymic(String patronymic);

    List<TeacherEntity> findByFullNameFirstNameAndFullNameLastName(
            String firstName, String lastName);

    List<TeacherEntity> findByFullNameFirstNameAndFullNameLastNameAndFullNamePatronymic(
            String firstName, String lastName, String patronymic);

    //Получение преподавателей-модераторов
    List<TeacherEntity> findByIsModeratorTrue();

    //Поиск преподавателя по дате урока


    //Проверка существования учителя по логину
    boolean existsByLogin(String login);

    //TODO: Сделать методы для (см. API документацию): api/teachers/update-schedule, api/teachers/notice.
}
