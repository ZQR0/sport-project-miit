package com.sport.project.service.interfaces.healthgroup;

import com.sport.project.dto.HealthGroupCreationDTO;
import com.sport.project.dto.HealthGroupDTO;
import com.sport.project.exception.EntityAlreadyExistsException;

/**
 * Сервис для создания медицинских групп здоровья.
 */
public interface HealthGroupCreationService {

    /**
     * Создание новой медицинской группы.
     *
     * @param dto данные для создания медицинской группы
     * @return данные созданной медицинской группы
     * @throws EntityAlreadyExistsException если медицинская группа с таким названием уже существует
     */
    HealthGroupDTO create(HealthGroupCreationDTO dto) throws EntityAlreadyExistsException;

    /**
     * Создание новой медицинской группы.
     *
     * @param name название медицинской группы
     * @param description описание медицинской группы
     * @return данные созданной медицинской группы
     * @throws EntityAlreadyExistsException если медицинская группа с таким названием уже существует
     */
    HealthGroupDTO create(String name, String description) throws EntityAlreadyExistsException;
}
