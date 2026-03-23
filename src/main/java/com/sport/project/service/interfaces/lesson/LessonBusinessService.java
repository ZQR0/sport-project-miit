package com.sport.project.service.interfaces.lesson;

import com.sport.project.dto.LessonDTO;
import com.sport.project.dto.StudentDTO;
import com.sport.project.dto.VisitDTO;
import com.sport.project.exception.EntityNotFoundException;

import java.util.List;
import java.util.Map;

/**
 * Сервис бизнес-логики для занятий.
 */
public interface LessonBusinessService {

    /**
     * Получение информации о посещаемости занятия.
     *
     * @param lessonId идентификатор занятия
     * @return список записей о посещении
     * @throws EntityNotFoundException если занятие не найдено
     */
    List<VisitDTO> getAttendance(Integer lessonId) throws EntityNotFoundException;

    /**
     * Отметка посещаемости студента на занятии.
     *
     * @param lessonId идентификатор занятия
     * @param studentLogin логин студента
     * @param present true если студент присутствовал, иначе false
     * @throws EntityNotFoundException если занятие или студент не найдены
     */
    void markAttendance(Integer lessonId, String studentLogin, boolean present) throws EntityNotFoundException;

    /**
     * Получение списка студентов, ожидаемых на занятии.
     *
     * @param lessonId идентификатор занятия
     * @return список студентов
     * @throws EntityNotFoundException если занятие не найдено
     */
    List<StudentDTO> getExpectedStudents(Integer lessonId) throws EntityNotFoundException;

    /**
     * Получение количества отметок о посещении на занятии.
     *
     * @param lessonId идентификатор занятия
     * @return количество отметок
     * @throws EntityNotFoundException если занятие не найдено
     */
    int getAttendanceCount(Integer lessonId) throws EntityNotFoundException;

    /**
     * Проверка возможности удаления занятия.
     *
     * @param lessonId идентификатор занятия
     * @return true если занятие не имеет записей о посещении и может быть удалено
     * @throws EntityNotFoundException если занятие не найдено
     */
    boolean canDelete(Integer lessonId) throws EntityNotFoundException;

    /**
     * Получение полной информации о занятии.
     *
     * @param lessonId идентификатор занятия
     * @return данные занятия с полной информацией
     * @throws EntityNotFoundException если занятие не найдено
     */
    LessonDTO getWithFullDetails(Integer lessonId) throws EntityNotFoundException;

    /**
     * Массовая отметка посещаемости для занятия.
     *
     * @param lessonId идентификатор занятия
     * @param attendanceMap мапа: логин студента → статус посещения
     * @throws EntityNotFoundException если занятие не найдено
     */
    void bulkMarkAttendance(Integer lessonId, Map<String, Boolean> attendanceMap) throws EntityNotFoundException;
}
