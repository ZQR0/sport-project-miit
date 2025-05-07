package com.sport.project.service.interfaces.student;

import com.sport.project.dao.entity.StudentEntity;

import java.util.Date;
import java.util.Map;

public interface StudentBusiness {
    Map<Date, String> getStudentSchedule(StudentEntity student);
}
