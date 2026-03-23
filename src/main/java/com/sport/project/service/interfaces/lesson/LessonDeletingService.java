package com.sport.project.service.interfaces.lesson;

import com.sport.project.exception.EntityNotFoundException;

import java.time.LocalDate;

/**
 * Сервис для удаления занятий.
 */
public interface LessonDeletingService {

    /**
     * Удаление занятия по идентификатору.
     *
     * @param id идентификатор занятия
     * @throws EntityNotFoundException если занятие не найдено
     */
    void deleteById(Integer id) throws EntityNotFoundException;

    /**
     * Удаление всех занятий дисциплины.
     *
     * @param disciplineId идентификатор дисциплины
     * @throws EntityNotFoundException если дисциплина не найдена
     */
    void deleteByDiscipline(Integer disciplineId) throws EntityNotFoundException;

    /**
     * Удаление всех занятий преподавателя.
     *
     * @param teacherId идентификатор преподавателя
     * @throws EntityNotFoundException если преподаватель не найден
     */
    void deleteByTeacher(Integer teacherId) throws EntityNotFoundException;

    /**
     * Удаление всех занятий по дате.
     *
     * @param date дата занятий
     * @throws EntityNotFoundException если занятия не найдены
     */
    void deleteByDate(LocalDate date) throws EntityNotFoundException;
}
