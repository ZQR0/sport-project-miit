package com.sport.project.service.interfaces.student;

import com.sport.project.dao.entity.TeacherEntity;
import com.sport.project.exception.EntityAlreadyExistsException;
import com.sport.project.exception.EntityNotFoundException;

/**
 * Сервис для обновления данных студента
 */
public interface StudentUpdatingService {

    /**
     * Обновление FSP студента
     *
     * @param newFSP новое FSP студента
     * @param login логин студента
     * @throws EntityNotFoundException если студент не найден
     */
    void updateFSP(String newFSP, String login) throws EntityNotFoundException;

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

    /**
     * Привязка студента к преподавателю
     *
     * @param teacher сущность преподавателя
     * @param login логин студента
     * @throws EntityNotFoundException если студент или преподаватель не найден
     */
    void updateStudentTeacher(TeacherEntity teacher, String login) throws EntityNotFoundException;
}
