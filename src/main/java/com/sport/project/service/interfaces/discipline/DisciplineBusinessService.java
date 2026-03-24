package com.sport.project.service.interfaces.discipline;

import com.sport.project.dto.LessonDTO;
import com.sport.project.exception.EntityNotFoundException;

import java.time.LocalDate;
import java.util.List;

/**
 * Сервис бизнес-логики для учебных дисциплин.
 */
public interface DisciplineBusinessService {

    /**
     * Получение количества занятий дисциплины.
     *
     * @param disciplineId идентификатор дисциплины
     * @return количество занятий
     * @throws EntityNotFoundException если дисциплина не найдена
     */
    int getLessonCount(Integer disciplineId) throws EntityNotFoundException;

    /**
     * Проверка возможности удаления дисциплины.
     *
     * @param disciplineId идентификатор дисциплины
     * @return true если дисциплина не содержит занятий и может быть удалена
     * @throws EntityNotFoundException если дисциплина не найдена
     */
    boolean canDelete(Integer disciplineId) throws EntityNotFoundException;

    /**
     * Получение занятий дисциплины с информацией о преподавателе.
     *
     * @param disciplineId идентификатор дисциплины
     * @return список занятий с преподавателем
     * @throws EntityNotFoundException если дисциплина не найдена
     */
    List<LessonDTO> getLessonsWithTeacher(Integer disciplineId) throws EntityNotFoundException;

    /**
     * Получение занятий дисциплины за период.
     *
     * @param disciplineId идентификатор дисциплины
     * @param from начальная дата периода
     * @param to конечная дата периода
     * @return список занятий за период
     * @throws EntityNotFoundException если дисциплина не найдена
     */
    List<LessonDTO> getLessonsByDateRange(Integer disciplineId, LocalDate from, LocalDate to) throws EntityNotFoundException;
}
