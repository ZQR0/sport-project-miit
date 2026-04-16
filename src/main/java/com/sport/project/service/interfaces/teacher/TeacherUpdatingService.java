package com.sport.project.service.interfaces.teacher;

import com.sport.project.exception.EntityAlreadyExistsException;
import com.sport.project.exception.EntityNotFoundException;

/**
 * Сервис для обновления данных учителя
 */
public interface TeacherUpdatingService {
    /**
     * Обновление полного имени учителя
     *
     * @param login логин учителя
     * @throws EntityNotFoundException если учителя не найден
     */
    void updateFullName(String firstName, String lastName, String patronymic, String login) throws EntityNotFoundException;

    /**
     * Обновление логина студента
     *
     * @param newLogin новый логин учителя
     * @param oldLogin текущий логин учителя
     * @throws EntityNotFoundException если учителя не найден
     * @throws EntityAlreadyExistsException если новый логин уже занят
     */
    void updateLogin(String newLogin, String oldLogin) throws EntityNotFoundException, EntityAlreadyExistsException;

    /**
     * Обновление логина студента
     *
     * @param login логин учителя
     * @param moderator новый статус модератора
     * @throws EntityNotFoundException если учителя не найден
     */
    void updateModerator(String login, boolean moderator) throws EntityNotFoundException;
}
