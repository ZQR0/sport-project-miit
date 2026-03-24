package com.sport.project.service.interfaces.group;

import com.sport.project.dto.GroupCreationDTO;
import com.sport.project.dto.GroupDTO;
import com.sport.project.exception.EntityAlreadyExistsException;

/**
 * Сервис для создания учебных групп.
 */
public interface GroupCreationService {

    /**
     * Создание новой учебной группы.
     *
     * @param dto данные для создания группы
     * @return данные созданной группы
     * @throws EntityAlreadyExistsException если группа с таким названием уже существует
     */
    GroupDTO create(GroupCreationDTO dto) throws EntityAlreadyExistsException;

    /**
     * Создание новой учебной группы.
     *
     * @param name название группы
     * @param institute название института
     * @return данные созданной группы
     * @throws EntityAlreadyExistsException если группа с таким названием уже существует
     */
    GroupDTO create(String name, String institute) throws EntityAlreadyExistsException;
}
