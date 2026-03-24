package com.sport.project.service.interfaces.section;

import com.sport.project.dto.StudentDTO;
import com.sport.project.exception.EntityNotFoundException;

import java.util.List;

/**
 * Сервис бизнес-логики для спортивных секций.
 */
public interface SectionBusinessService {

    /**
     * Получение количества студентов в секции.
     *
     * @param sectionId идентификатор секции
     * @return количество студентов
     * @throws EntityNotFoundException если секция не найдена
     */
    int getStudentCount(Integer sectionId) throws EntityNotFoundException;

    /**
     * Проверка возможности удаления секции.
     *
     * @param sectionId идентификатор секции
     * @return true если секция не содержит студентов и может быть удалена
     * @throws EntityNotFoundException если секция не найдена
     */
    boolean canDelete(Integer sectionId) throws EntityNotFoundException;

    /**
     * Добавление студента в секцию.
     *
     * @param sectionId идентификатор секции
     * @param studentLogin логин студента
     * @throws EntityNotFoundException если секция или студент не найдены
     */
    void addStudent(Integer sectionId, String studentLogin) throws EntityNotFoundException;

    /**
     * Удаление студента из секции.
     *
     * @param sectionId идентификатор секции
     * @param studentLogin логин студента
     * @throws EntityNotFoundException если секция или студент не найдены
     */
    void removeStudent(Integer sectionId, String studentLogin) throws EntityNotFoundException;

    /**
     * Получение студентов секции с информацией о посещаемости.
     *
     * @param sectionId идентификатор секции
     * @return список студентов с посещаемостью
     * @throws EntityNotFoundException если секция не найдена
     */
    List<StudentDTO> getStudentsWithAttendance(Integer sectionId) throws EntityNotFoundException;
}
