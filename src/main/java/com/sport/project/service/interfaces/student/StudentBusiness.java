package com.sport.project.service.interfaces.student;

import com.sport.project.dao.entity.StudentEntity;

import java.time.LocalDate;
import java.util.Map;

public interface StudentBusiness {
    Map<LocalDate, String> getStudentSchedule(StudentEntity student);
}
