package com.sport.project.service.interfaces.visit;

import com.sport.project.dto.VisitCreationDTO;
import com.sport.project.dto.VisitDTO;
import com.sport.project.exception.EntityAlreadyExistsException;

/**
 * Сервис для создания записей о посещениях занятий.
 */
public interface VisitCreationService {

    /**
     * Создание новой записи о посещении.
     *
     * @param dto данные для создания записи о посещении
     * @return данные созданной записи
     * @throws EntityAlreadyExistsException если запись уже существует
     */
    VisitDTO create(VisitCreationDTO dto) throws EntityAlreadyExistsException;

    /**
     * Создание новой записи о посещении.
     *
     * @param studentLogin логин студента
     * @param lessonId идентификатор занятия
     * @param isExists true если студент присутствовал, иначе false
     * @return данные созданной записи
     * @throws EntityAlreadyExistsException если запись уже существует
     */
    VisitDTO create(String studentLogin, Integer lessonId, boolean isExists) throws EntityAlreadyExistsException;
}
