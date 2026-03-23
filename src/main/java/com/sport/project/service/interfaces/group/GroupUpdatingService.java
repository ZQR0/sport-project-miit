package com.sport.project.service.interfaces.group;

import com.sport.project.dto.GroupDTO;
import com.sport.project.exception.EntityNotFoundException;

/**
 * Сервис для обновления данных учебных групп.
 */
public interface GroupUpdatingService {

    /**
     * Обновление названия группы.
     *
     * @param id идентификатор группы
     * @param name новое название
     * @return обновлённые данные группы
     * @throws EntityNotFoundException если группа не найдена
     */
    GroupDTO updateName(Integer id, String name) throws EntityNotFoundException;

    /**
     * Обновление института группы.
     *
     * @param id идентификатор группы
     * @param institute новое название института
     * @return обновлённые данные группы
     * @throws EntityNotFoundException если группа не найдена
     */
    GroupDTO updateInstitute(Integer id, String institute) throws EntityNotFoundException;
}
