package com.sport.project.service.interfaces.visit;

import com.sport.project.dto.AttendanceInfo;
import com.sport.project.dto.StudentDTO;
import com.sport.project.exception.EntityNotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Сервис бизнес-логики для записей о посещениях занятий.
 */
public interface VisitBusinessService {

    /**
     * Получение карты посещаемости студента.
     *
     * @param studentLogin логин студента
     * @return мапа: дата → статус посещения (true - присутствовал)
     * @throws EntityNotFoundException если студент не найден
     */
    Map<LocalDate, List<AttendanceInfo>> getStudentAttendanceMap(String studentLogin) throws EntityNotFoundException;

    /**
     * Получение общего количества посещений студента.
     *
     * @param studentLogin логин студента
     * @return количество посещений
     * @throws EntityNotFoundException если студент не найден
     */
    int getTotalVisits(String studentLogin) throws EntityNotFoundException;

    /**
     * Получение общего количества пропусков студента.
     *
     * @param studentLogin логин студента
     * @return количество пропусков
     * @throws EntityNotFoundException если студент не найден
     */
    int getTotalAbsences(String studentLogin) throws EntityNotFoundException;

    /**
     * Получение процента посещаемости студента.
     *
     * @param studentLogin логин студента
     * @return процент посещаемости (0-100)
     * @throws EntityNotFoundException если студент не найден
     */
    double getAttendancePercentage(String studentLogin) throws EntityNotFoundException;

    /**
     * Получение списка студентов, отсутствовавших на занятии.
     *
     * @param lessonId идентификатор занятия
     * @return список отсутствовавших студентов
     * @throws EntityNotFoundException если занятие не найдено
     */
    List<StudentDTO> getAbsentStudentsForLesson(Integer lessonId) throws EntityNotFoundException;

    /**
     * Массовая отметка посещаемости для занятия.
     *
     * @param lessonId идентификатор занятия
     * @param attendanceMap мапа: логин студента → статус посещения
     * @throws EntityNotFoundException если занятие не найдено
     */
    void bulkMarkAttendance(Integer lessonId, Map<String, Boolean> attendanceMap) throws EntityNotFoundException;
}
