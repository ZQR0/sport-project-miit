package com.sport.project.service.interfaces.student;

import com.sport.project.dao.entity.TeacherEntity;
import com.sport.project.exception.EntityAlreadyExistsException;
import com.sport.project.exception.EntityNotFoundException;

/**
 * Сервис для обновления данных студента
 */
public interface StudentUpdatingService {

    /**
     * Обновление полного имени студента
     *
     * @param login логин студента
     * @throws EntityNotFoundException если студент не найден
     */
    void updateFullName(String firstName, String lastName, String patronymic, String login) throws EntityNotFoundException;

    /**
     * Обновление логина студента
     *
     * @param newLogin новый логин студента
     * @param login текущий логин студента
     * @throws EntityNotFoundException если студент не найден
     * @throws EntityAlreadyExistsException если новый логин уже занят
     */
    void updateLogin(String newLogin, String login) throws EntityNotFoundException, EntityAlreadyExistsException;

    /**
     * Обновление группы здоровья студента
     *
     * @param newHealthGroup новая группа здоровья
     * @param login логин студента
     * @throws EntityNotFoundException если студент не найден
     */
    void updateHealthGroup(int newHealthGroup, String login) throws EntityNotFoundException;
}
