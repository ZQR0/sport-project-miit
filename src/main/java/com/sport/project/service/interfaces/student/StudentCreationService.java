package com.sport.project.service.interfaces.student;

import com.sport.project.dto.StudentCreationDTO;
import com.sport.project.dto.StudentDTO;
import com.sport.project.exception.EntityAlreadyExistsException;

/**
 * Сервис для создания студента
 */
public interface StudentCreationService {

    /**
     *Создание нового студента
     *
     * @param dto данные для создания секции
     * @return данные созданного студента
     * @throws EntityAlreadyExistsException если студент с таким названием уже существует
     */
    StudentDTO create(StudentCreationDTO dto) throws EntityAlreadyExistsException;
}
