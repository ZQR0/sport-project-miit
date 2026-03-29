package com.sport.project.service;

import com.sport.project.dto.SectionDTO;
import com.sport.project.dto.StudentDTO;
import com.sport.project.exception.EntityNotFoundException;

import java.util.List;

/**
 * Сервис для работы со спортивными секциями.
 * Предоставляет методы для получения данных о секциях.
 */
public interface SectionService {

    /**
     * Получение секции по идентификатору.
     *
     * @param id идентификатор секции
     * @return данные секции
     * @throws EntityNotFoundException если секция не найдена
     */
    SectionDTO findById(Integer id) throws EntityNotFoundException;

    /**
     * Получение секции по названию.
     *
     * @param name название секции
     * @return данные секции
     * @throws EntityNotFoundException если секция не найдена
     */
    SectionDTO findByName(String name) throws EntityNotFoundException;

    /**
     * Получение всех секций.
     *
     * @return список всех секций
     */
    List<SectionDTO> findAll();

    /**
     * Получение всех студентов секции.
     *
     * @param sectionId идентификатор секции
     * @return список студентов секции
     * @throws EntityNotFoundException если секция не найдена
     */
    List<StudentDTO> getStudents(Integer sectionId) throws EntityNotFoundException;

    //todo метод для получения студентов по названию секции
    /**
     * Получение всех студентов секции.
     *
     * @param sectionName название секции
     * @return список студентов секции
     * @throws EntityNotFoundException если секция не найдена
     */
    List<StudentDTO> getStudents(String sectionName) throws EntityNotFoundException;

    /**
     * Проверка существования секции по названию.
     *
     * @param name название секции
     * @return true если секция существует, иначе false
     */
    boolean existsByName(String name);
}
