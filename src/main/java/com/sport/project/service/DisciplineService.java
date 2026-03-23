package com.sport.project.service;

import com.sport.project.dto.DisciplineDTO;
import com.sport.project.dto.LessonDTO;
import com.sport.project.exception.EntityNotFoundException;

import java.util.List;

/**
 * Сервис для работы с учебными дисциплинами.
 * Предоставляет методы для получения данных о дисциплинах.
 */
public interface DisciplineService {

    /**
     * Получение дисциплины по идентификатору.
     *
     * @param id идентификатор дисциплины
     * @return данные дисциплины
     * @throws EntityNotFoundException если дисциплина не найдена
     */
    DisciplineDTO findById(Integer id) throws EntityNotFoundException;

    /**
     * Получение дисциплины по названию.
     *
     * @param name название дисциплины
     * @return данные дисциплины
     * @throws EntityNotFoundException если дисциплина не найдена
     */
    DisciplineDTO findByName(String name) throws EntityNotFoundException;

    /**
     * Получение всех дисциплин.
     *
     * @return список всех дисциплин
     */
    List<DisciplineDTO> findAll();

    /**
     * Получение всех занятий дисциплины.
     *
     * @param disciplineId идентификатор дисциплины
     * @return список занятий дисциплины
     * @throws EntityNotFoundException если дисциплина не найдена
     */
    List<LessonDTO> getLessons(Integer disciplineId) throws EntityNotFoundException;

    /**
     * Проверка существования дисциплины по названию.
     *
     * @param name название дисциплины
     * @return true если дисциплина существует, иначе false
     */
    boolean existsByName(String name);
}
