package com.sport.project.service.interfaces.visit;

import com.sport.project.dto.VisitDTO;
import com.sport.project.exception.EntityNotFoundException;

/**
 * Сервис для обновления данных о посещениях занятий.
 */
public interface VisitUpdatingService {

    /**
     * Обновление статуса посещения.
     *
     * @param visitId идентификатор записи о посещении
     * @param isExists новый статус (true - присутствовал, false - отсутствовал)
     * @return обновлённые данные о посещении
     * @throws EntityNotFoundException если запись не найдена
     */
    VisitDTO updateStatus(Integer visitId, boolean isExists) throws EntityNotFoundException;
}
