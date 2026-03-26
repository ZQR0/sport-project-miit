package com.sport.project.service.interfaces.student;

import com.sport.project.exception.EntityNotFoundException;

/**
 * Сервис удаления студента
 */
public interface StudentDeletingService {

    /**
     * Удаление студента по идентификатору
     *
     * @param id идентификатор студента
     * @throws EntityNotFoundException если студент не найден
     */
    void deleteByID(int id) throws EntityNotFoundException;

    /**
     * Удаление студента по FSP
     *
     * @param fsp FSP студента
     * @throws EntityNotFoundException если студент не найден
     */
    void deleteByFSP(String fsp) throws EntityNotFoundException;

    /**
     * Удаление студента по логину
     *
     * @param login логин студента
     * @throws EntityNotFoundException если студент не найден
     */
    void deleteByLogin(String login) throws EntityNotFoundException;
}
