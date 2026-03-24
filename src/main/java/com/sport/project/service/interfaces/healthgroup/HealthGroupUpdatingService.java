package com.sport.project.service.interfaces.healthgroup;

import com.sport.project.dto.HealthGroupDTO;
import com.sport.project.exception.EntityNotFoundException;

/**
 * Сервис для обновления данных медицинских групп здоровья.
 */
public interface HealthGroupUpdatingService {

    /**
     * Обновление названия медицинской группы.
     *
     * @param id идентификатор медицинской группы
     * @param name новое название
     * @return обновлённые данные медицинской группы
     * @throws EntityNotFoundException если медицинская группа не найдена
     */
    HealthGroupDTO updateName(Integer id, String name) throws EntityNotFoundException;

    /**
     * Обновление описания медицинской группы.
     *
     * @param id идентификатор медицинской группы
     * @param description новое описание
     * @return обновлённые данные медицинской группы
     * @throws EntityNotFoundException если медицинская группа не найдена
     */
    HealthGroupDTO updateDescription(Integer id, String description) throws EntityNotFoundException;
}
