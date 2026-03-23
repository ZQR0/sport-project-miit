package com.sport.project.service.interfaces.section;

import com.sport.project.dto.SectionCreationDTO;
import com.sport.project.dto.SectionDTO;
import com.sport.project.exception.EntityAlreadyExistsException;

/**
 * Сервис для создания спортивных секций.
 */
public interface SectionCreationService {

    /**
     * Создание новой спортивной секции.
     *
     * @param dto данные для создания секции
     * @return данные созданной секции
     * @throws EntityAlreadyExistsException если секция с таким названием уже существует
     */
    SectionDTO create(SectionCreationDTO dto) throws EntityAlreadyExistsException;

    /**
     * Создание новой спортивной секции.
     *
     * @param name название секции
     * @param description описание секции
     * @return данные созданной секции
     * @throws EntityAlreadyExistsException если секция с таким названием уже существует
     */
    SectionDTO create(String name, String description) throws EntityAlreadyExistsException;
}
