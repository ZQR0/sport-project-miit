package com.sport.project.service.interfaces.healthgroup;

import com.sport.project.exception.EntityNotFoundException;

/**
 * Сервис для удаления медицинских групп здоровья.
 */
public interface HealthGroupDeletingService {

    /**
     * Удаление медицинской группы по идентификатору.
     *
     * @param id идентификатор медицинской группы
     * @throws EntityNotFoundException если медицинская группа не найдена
     */
    void deleteById(Integer id) throws EntityNotFoundException;

    /**
     * Удаление медицинской группы по названию.
     *
     * @param name название медицинской группы
     * @throws EntityNotFoundException если медицинская группа не найдена
     */
    void deleteByName(String name) throws EntityNotFoundException;
}
