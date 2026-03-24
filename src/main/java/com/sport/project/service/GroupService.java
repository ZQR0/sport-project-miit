package com.sport.project.service;

import com.sport.project.dto.GroupDTO;
import com.sport.project.dto.StudentDTO;
import com.sport.project.exception.EntityNotFoundException;

import java.util.List;

/**
 * Сервис для работы с учебными группами.
 * Предоставляет методы для получения данных о группах.
 */
public interface GroupService {

    /**
     * Получение группы по идентификатору.
     *
     * @param id идентификатор группы
     * @return данные группы
     * @throws EntityNotFoundException если группа не найдена
     */
    GroupDTO findById(Integer id) throws EntityNotFoundException;

    /**
     * Получение группы по названию.
     *
     * @param name название группы
     * @return данные группы
     * @throws EntityNotFoundException если группа не найдена
     */
    GroupDTO findByName(String name) throws EntityNotFoundException;

    /**
     * Получение всех групп.
     *
     * @return список всех групп
     */
    List<GroupDTO> findAll();

    /**
     * Получение всех групп института.
     *
     * @param institute название института
     * @return список групп института
     */
    List<GroupDTO> findByInstitute(String institute);

    /**
     * Получение всех студентов группы.
     *
     * @param groupId идентификатор группы
     * @return список студентов группы
     * @throws EntityNotFoundException если группа не найдена
     */
    List<StudentDTO> getStudents(Integer groupId) throws EntityNotFoundException;

    /**
     * Проверка существования группы по названию.
     *
     * @param name название группы
     * @return true если группа существует, иначе false
     */
    boolean existsByName(String name);
}
