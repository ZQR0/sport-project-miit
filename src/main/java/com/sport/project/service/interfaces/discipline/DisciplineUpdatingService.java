package com.sport.project.service.interfaces.discipline;

import com.sport.project.dto.DisciplineDTO;
import com.sport.project.exception.EntityNotFoundException;

/**
 * Сервис для обновления данных учебных дисциплин.
 */
public interface DisciplineUpdatingService {

    /**
     * Обновление названия дисциплины.
     *
     * @param id идентификатор дисциплины
     * @param name новое название
     * @return обновлённые данные дисциплины
     * @throws EntityNotFoundException если дисциплина не найдена
     */
    DisciplineDTO updateName(Integer id, String name) throws EntityNotFoundException;
}
