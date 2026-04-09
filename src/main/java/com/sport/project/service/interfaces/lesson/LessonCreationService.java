package com.sport.project.service.interfaces.lesson;

import com.sport.project.dto.LessonCreationDTO;
import com.sport.project.dto.LessonDTO;
import com.sport.project.exception.EntityAlreadyExistsException;

import java.time.LocalDate;
import java.util.Date;

/**
 * Сервис для создания занятий.
 */
public interface LessonCreationService {

    /**
     * Создание нового занятия.
     *
     * @param dto данные для создания занятия
     * @return данные созданного занятия
     * @throws EntityAlreadyExistsException если занятие с такими параметрами уже существует
     */
    LessonDTO create(LessonCreationDTO dto) throws EntityAlreadyExistsException;

    /**
     * Создание нового занятия.
     *
     * @param dateOfLesson дата занятия
     * @param teacherId идентификатор преподавателя
     * @param disciplineId идентификатор дисциплины
     * @return данные созданного занятия
     * @throws EntityAlreadyExistsException если занятие с такими параметрами уже существует
     */
    LessonDTO create(LocalDate dateOfLesson, Integer teacherId, Integer disciplineId) throws EntityAlreadyExistsException;
}
