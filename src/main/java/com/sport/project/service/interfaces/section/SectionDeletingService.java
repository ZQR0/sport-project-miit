package com.sport.project.service.interfaces.section;

import com.sport.project.exception.EntityNotFoundException;

/**
 * Сервис для удаления спортивных секций.
 */
public interface SectionDeletingService {

    /**
     * Удаление секции по идентификатору.
     *
     * @param id идентификатор секции
     * @throws EntityNotFoundException если секция не найдена
     */
    void deleteById(Integer id) throws EntityNotFoundException;

    /**
     * Удаление секции по названию.
     *
     * @param name название секции
     * @throws EntityNotFoundException если секция не найдена
     */
    void deleteByName(String name) throws EntityNotFoundException;
}
