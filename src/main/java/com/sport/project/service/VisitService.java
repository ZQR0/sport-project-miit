package com.sport.project.service;

import com.sport.project.dto.VisitDTO;
import com.sport.project.exception.EntityNotFoundException;

import java.time.LocalDate;
import java.util.List;

/**
 * Сервис для работы с посещаемостью.
 * Предоставляет методы для получения данных о посещениях занятий.
 */
public interface VisitService {

    /**
     * Получение записи о посещении по идентификатору.
     *
     * @param id идентификатор записи о посещении
     * @return данные о посещении
     * @throws EntityNotFoundException если запись не найдена
     */
    VisitDTO findById(Integer id) throws EntityNotFoundException;

    /**
     * Получение записи о посещении по студенту и занятию.
     *
     * @param studentLogin логин студента
     * @param lessonId идентификатор занятия
     * @return данные о посещении
     * @throws EntityNotFoundException если запись не найдена
     */
    VisitDTO findByStudentAndLesson(String studentLogin, Integer lessonId) throws EntityNotFoundException;

    /**
     * Получение всех записей о посещениях.
     *
     * @return список всех записей о посещениях
     */
    List<VisitDTO> findAll();

    /**
     * Получение всех записей о посещениях студента.
     *
     * @param studentLogin логин студента
     * @return список записей о посещениях студента
     */
    List<VisitDTO> findByStudent(String studentLogin);

    /**
     * Получение всех записей о посещениях занятия.
     *
     * @param lessonId идентификатор занятия
     * @return список записей о посещениях занятия
     */
    List<VisitDTO> findByLesson(Integer lessonId);

    /**
     * Получение записей о посещениях за период.
     *
     * @param from начальная дата периода
     * @param to конечная дата периода
     * @return список записей о посещениях за период
     */
    List<VisitDTO> findByDateRange(LocalDate from, LocalDate to);

    /**
     * Проверка существования записи о посещении по идентификатору.
     *
     * @param id идентификатор записи о посещении
     * @return true если запись существует, иначе false
     */
    boolean existsById(Integer id);
}
