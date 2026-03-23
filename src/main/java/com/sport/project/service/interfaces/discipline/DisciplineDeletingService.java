package com.sport.project.service.interfaces.discipline;

import com.sport.project.exception.EntityNotFoundException;

/**
 * Сервис для удаления учебных дисциплин.
 */
public interface DisciplineDeletingService {

    /**
     * Удаление дисциплины по идентификатору.
     *
     * @param id идентификатор дисциплины
     * @throws EntityNotFoundException если дисциплина не найдена
     */
    void deleteById(Integer id) throws EntityNotFoundException;

    /**
     * Удаление дисциплины по названию.
     *
     * @param name название дисциплины
     * @throws EntityNotFoundException если дисциплина не найдена
     */
    void deleteByName(String name) throws EntityNotFoundException;
}
