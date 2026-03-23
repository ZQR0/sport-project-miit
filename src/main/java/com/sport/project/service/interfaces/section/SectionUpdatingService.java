package com.sport.project.service.interfaces.section;

import com.sport.project.dto.SectionDTO;
import com.sport.project.exception.EntityNotFoundException;

/**
 * Сервис для обновления данных спортивных секций.
 */
public interface SectionUpdatingService {

    /**
     * Обновление названия секции.
     *
     * @param id идентификатор секции
     * @param name новое название
     * @return обновлённые данные секции
     * @throws EntityNotFoundException если секция не найдена
     */
    SectionDTO updateName(Integer id, String name) throws EntityNotFoundException;

    /**
     * Обновление описания секции.
     *
     * @param id идентификатор секции
     * @param description новое описание
     * @return обновлённые данные секции
     * @throws EntityNotFoundException если секция не найдена
     */
    SectionDTO updateDescription(Integer id, String description) throws EntityNotFoundException;
}
