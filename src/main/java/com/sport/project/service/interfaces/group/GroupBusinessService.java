package com.sport.project.service.interfaces.group;

import com.sport.project.dto.StudentDTO;
import com.sport.project.exception.EntityNotFoundException;

import java.time.LocalDate;
import java.util.List;

/**
 * Сервис бизнес-логики для учебных групп.
 */
public interface GroupBusinessService {

    /**
     * Получение количества студентов в группе.
     *
     * @param groupId идентификатор группы
     * @return количество студентов
     * @throws EntityNotFoundException если группа не найдена
     */
    int getStudentCount(Integer groupId) throws EntityNotFoundException;

    /**
     * Проверка, является ли группа пустой (не содержит студентов).
     *
     * @param groupId идентификатор группы
     * @return true если группа пустая, иначе false
     * @throws EntityNotFoundException если группа не найдена
     */
    boolean isEmpty(Integer groupId) throws EntityNotFoundException;

    /**
     * Получение студентов группы с информацией о посещаемости за период.
     *
     * @param groupId идентификатор группы
     * @param from начальная дата периода
     * @param to конечная дата периода
     * @return список студентов с посещаемостью
     * @throws EntityNotFoundException если группа не найдена
     */
    List<StudentDTO> getStudentsWithAttendance(Integer groupId, LocalDate from, LocalDate to) throws EntityNotFoundException;

    /**
     * Перевод всех студентов из одной группы в другую.
     *
     * @param fromGroupId идентификатор исходной группы
     * @param toGroupId идентификатор целевой группы
     * @throws EntityNotFoundException если одна из групп не найдена
     */
    void transferStudents(Integer fromGroupId, Integer toGroupId) throws EntityNotFoundException;
}
