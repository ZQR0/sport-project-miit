package com.sport.project.service.interfaces.lesson;

import com.sport.project.dto.LessonDTO;
import com.sport.project.exception.EntityNotFoundException;

import java.util.Date;

/**
 * Сервис для обновления данных занятий.
 */
public interface LessonUpdatingService {

    /**
     * Обновление даты занятия.
     *
     * @param lessonId идентификатор занятия
     * @param newDate новая дата занятия
     * @return обновлённые данные занятия
     * @throws EntityNotFoundException если занятие не найдено
     */
    LessonDTO updateDate(Integer lessonId, Date newDate) throws EntityNotFoundException;

    /**
     * Обновление преподавателя занятия.
     *
     * @param lessonId идентификатор занятия
     * @param teacherId идентификатор нового преподавателя
     * @return обновлённые данные занятия
     * @throws EntityNotFoundException если занятие не найдено
     */
    LessonDTO updateTeacher(Integer lessonId, Integer teacherId) throws EntityNotFoundException;

    /**
     * Обновление дисциплины занятия.
     *
     * @param lessonId идентификатор занятия
     * @param disciplineId идентификатор новой дисциплины
     * @return обновлённые данные занятия
     * @throws EntityNotFoundException если занятие не найдено
     */
    LessonDTO updateDiscipline(Integer lessonId, Integer disciplineId) throws EntityNotFoundException;
}
