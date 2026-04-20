package com.sport.project.dto;

import java.time.LocalTime;

/**
 * Информация о конкретном посещении для метода получения посещений в {@link com.sport.project.service.interfaces.visit.VisitBusinessService}
 * */
public record AttendanceInfo(Boolean isExists, LocalTime startAt, LocalTime endAt) { }
