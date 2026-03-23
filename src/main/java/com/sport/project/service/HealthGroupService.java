package com.sport.project.service;

import com.sport.project.dto.HealthGroupDTO;
import com.sport.project.dto.StudentDTO;
import com.sport.project.exception.EntityNotFoundException;

import java.util.List;

/**
 * Сервис для работы с медицинскими группами здоровья.
 * Предоставляет методы для получения данных о медицинских группах.
 */
public interface HealthGroupService {

    /**
     * Получение медицинской группы по идентификатору.
     *
     * @param id идентификатор медицинской группы
     * @return данные медицинской группы
     * @throws EntityNotFoundException если медицинская группа не найдена
     */
    HealthGroupDTO findById(Integer id) throws EntityNotFoundException;

    /**
     * Получение медицинской группы по названию.
     *
     * @param name название медицинской группы
     * @return данные медицинской группы
     * @throws EntityNotFoundException если медицинская группа не найдена
     */
    HealthGroupDTO findByName(String name) throws EntityNotFoundException;

    /**
     * Получение всех медицинских групп.
     *
     * @return список всех медицинских групп
     */
    List<HealthGroupDTO> findAll();

    /**
     * Получение всех студентов медицинской группы.
     *
     * @param healthGroupId идентификатор медицинской группы
     * @return список студентов медицинской группы
     * @throws EntityNotFoundException если медицинская группа не найдена
     */
    List<StudentDTO> getStudents(Integer healthGroupId) throws EntityNotFoundException;

    /**
     * Проверка существования медицинской группы по названию.
     *
     * @param name название медицинской группы
     * @return true если медицинская группа существует, иначе false
     */
    boolean existsByName(String name);
}
