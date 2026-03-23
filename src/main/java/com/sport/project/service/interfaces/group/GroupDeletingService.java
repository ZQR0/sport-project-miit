package com.sport.project.service.interfaces.group;

import com.sport.project.exception.EntityNotFoundException;

/**
 * Сервис для удаления учебных групп.
 */
public interface GroupDeletingService {

    /**
     * Удаление группы по идентификатору.
     *
     * @param id идентификатор группы
     * @throws EntityNotFoundException если группа не найдена
     */
    void deleteById(Integer id) throws EntityNotFoundException;

    /**
     * Удаление группы по названию.
     *
     * @param name название группы
     * @throws EntityNotFoundException если группа не найдена
     */
    void deleteByName(String name) throws EntityNotFoundException;
}
