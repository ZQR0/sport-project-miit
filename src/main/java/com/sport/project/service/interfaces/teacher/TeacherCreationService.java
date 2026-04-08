package com.sport.project.service.interfaces.teacher;

import com.sport.project.dto.TeacherCreationDTO;
import com.sport.project.dto.TeacherDTO;
import com.sport.project.exception.EntityAlreadyExistsException;

/**
 * Сервис для создания учителя
 */
public interface TeacherCreationService {

    /**
     * Создание нового учителя
     *
     * @param dto данные для создания учителя
     * @return данные созданного учителя
     * @throws EntityAlreadyExistsException если учитель с таким названием уже существует
     */
    TeacherDTO create(TeacherCreationDTO dto) throws EntityAlreadyExistsException;
}
