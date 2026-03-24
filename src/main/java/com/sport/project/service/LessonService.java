package com.sport.project.service;

import com.sport.project.dto.LessonDTO;
import com.sport.project.exception.EntityNotFoundException;

import java.time.LocalDate;
import java.util.List;

/**
 * Сервис для работы с занятиями.
 * Предоставляет методы для получения данных о занятиях.
 */
public interface LessonService {

    /**
     * Получение занятия по идентификатору.
     *
     * @param id идентификатор занятия
     * @return данные занятия
     * @throws EntityNotFoundException если занятие не найдено
     */
    LessonDTO findById(Integer id) throws EntityNotFoundException;

    /**
     * Получение занятия по дисциплине и дате.
     *
     * @param disciplineId идентификатор дисциплины
     * @param date дата занятия
     * @return данные занятия
     * @throws EntityNotFoundException если занятие не найдено
     */
    LessonDTO findByDisciplineAndDate(Integer disciplineId, LocalDate date) throws EntityNotFoundException;

    /**
     * Получение всех занятий.
     *
     * @return список всех занятий
     */
    List<LessonDTO> findAll();

    /**
     * Получение всех занятий по дате.
     *
     * @param date дата занятий
     * @return список занятий
     */
    List<LessonDTO> findByDate(LocalDate date);

    /**
     * Получение всех занятий преподавателя.
     *
     * @param teacherId идентификатор преподавателя
     * @return список занятий преподавателя
     */
    List<LessonDTO> findByTeacher(Integer teacherId);

    /**
     * Получение всех занятий дисциплины.
     *
     * @param disciplineId идентификатор дисциплины
     * @return список занятий дисциплины
     */
    List<LessonDTO> findByDiscipline(Integer disciplineId);

    /**
     * Получение занятий за период.
     *
     * @param from начальная дата периода
     * @param to конечная дата периода
     * @return список занятий за период
     */
    List<LessonDTO> findByDateRange(LocalDate from, LocalDate to);

    /**
     * Проверка существования занятия по идентификатору.
     *
     * @param id идентификатор занятия
     * @return true если занятие существует, иначе false
     */
    boolean existsById(Integer id);
}
