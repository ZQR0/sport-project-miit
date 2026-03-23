package com.sport.project.service.interfaces.discipline;

import com.sport.project.dto.DisciplineCreationDTO;
import com.sport.project.dto.DisciplineDTO;
import com.sport.project.exception.EntityAlreadyExistsException;

/**
 * Сервис для создания учебных дисциплин.
 */
public interface DisciplineCreationService {

    /**
     * Создание новой учебной дисциплины.
     *
     * @param dto данные для создания дисциплины
     * @return данные созданной дисциплины
     * @throws EntityAlreadyExistsException если дисциплина с таким названием уже существует
     */
    DisciplineDTO create(DisciplineCreationDTO dto) throws EntityAlreadyExistsException;

    /**
     * Создание новой учебной дисциплины.
     *
     * @param name название дисциплины
     * @return данные созданной дисциплины
     * @throws EntityAlreadyExistsException если дисциплина с таким названием уже существует
     */
    DisciplineDTO create(String name) throws EntityAlreadyExistsException;
}
