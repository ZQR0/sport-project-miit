package com.sport.project.service.interfaces.visit;

import com.sport.project.exception.EntityNotFoundException;

/**
 * Сервис для удаления записей о посещениях занятий.
 */
public interface VisitDeletingService {

    /**
     * Удаление записи о посещении по идентификатору.
     *
     * @param id идентификатор записи о посещении
     * @throws EntityNotFoundException если запись не найдена
     */
    void deleteById(Integer id) throws EntityNotFoundException;

    /**
     * Удаление всех записей о посещениях студента.
     *
     * @param studentLogin логин студента
     * @throws EntityNotFoundException если студент не найден
     */
    void deleteByStudent(String studentLogin) throws EntityNotFoundException;

    /**
     * Удаление всех записей о посещениях занятия.
     *
     * @param lessonId идентификатор занятия
     * @throws EntityNotFoundException если занятие не найдено
     */
    void deleteByLesson(Integer lessonId) throws EntityNotFoundException;
}
