package com.sport.project.service.interfaces.teacher;

import com.sport.project.exception.EntityNotFoundException;

/**
 * Сервис удаления учителя
 */
public interface TeacherDeletingService {
    /**
     * Удаление учителя по идентификатору
     *
     * @param id идентификатор учителя
     * @throws EntityNotFoundException если учитель не найден
     */
    void deleteByID(int id) throws EntityNotFoundException;

    /**
     * Удаление учителя по логину
     *
     * @param login логин учителя
     * @throws EntityNotFoundException если учителя не найден
     */
    void deleteByLogin(String login) throws EntityNotFoundException;
}
