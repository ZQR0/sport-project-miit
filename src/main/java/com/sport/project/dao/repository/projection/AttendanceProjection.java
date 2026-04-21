package com.sport.project.dao.repository.projection;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Проекция посещения для получения мапы посещений в бизнес логики {@link com.sport.project.service.interfaces.visit.VisitBusinessService}.
 * Проекция аналогична запросу SELECT поле1, поле2, поле3 FROM таблица.
 * Ну то есть обычная SQL-проекция.
 * Проекция была выбрана из соображений оптимизации, чтобы при получении даты занятия, времени проведения и данных посещения
 * не было N+2 проблемы, т.к. для каждой сущности будет вызываться много подзапросов.
 * Использование посмотреть можно в {@link com.sport.project.dao.repository.VisitsRepository}
 * */
public interface AttendanceProjection {
    LocalDate getLessonDate();
    Boolean getIsExists();
    LocalTime getStartAt();
    LocalTime getEndAt();
}
