package com.sport.project.service.interfaces.student;

import com.sport.project.dao.entity.StudentEntity;

import java.time.LocalDate;
import java.util.Map;

/**
 * Сервис бизнес логики для студента
 */
public interface StudentBusinessService {

    /**
     * Получение расписания занятий студента
     *
     * @param login логин студента
     * @return map: дата → название занятия/дисциплины
     */
    Map<LocalDate, String> getStudentSchedule(String login);
}
